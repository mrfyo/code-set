package com.feyon.codeset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.feyon.codeset.entity.*;
import com.feyon.codeset.exception.AdminException;
import com.feyon.codeset.exception.EntityException;
import com.feyon.codeset.form.QuestionForm;
import com.feyon.codeset.mapper.*;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.service.QuestionDetailService;
import com.feyon.codeset.service.QuestionService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.util.PageUtils;
import com.feyon.codeset.util.UserContext;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionDetailVO;
import com.feyon.codeset.vo.QuestionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    private final QuestionStatisticMapper questionStatisticMapper;

    private final QuestionTagMapper questionTagMapper;

    private final TagMapper tagMapper;

    private final QuestionDetailService questionDetailService;

    private final QuestionLikeMapper questionLikeMapper;

    private final SubmissionMapper submissionMapper;


    @Override
    @Transactional(rollbackFor = AdminException.class)
    public void save(QuestionForm form) {
        beforeCheck(form);

        Question question = new Question();
        question.setNumber(questionMapper.nextNumber());
        question.setTitle(form.getTitle());
        question.setDifficulty(form.getDifficulty());
        questionMapper.insert(question);
        Integer questionId = question.getId();
        if (questionId == null) {
            throw new AdminException("新增题目失败");
        }

        QuestionStatistic statistic = new QuestionStatistic();
        statistic.setId(questionId);
        statistic.setSolution(0L);
        statistic.setFailSubmission(0L);
        statistic.setSuccessSubmission(0L);
        if (questionStatisticMapper.insert(statistic) != 1) {
            throw new AdminException("新增题目失败");
        }

        List<Integer> tagIdList = form.getTagIdList();
        if (!ObjectUtils.isEmpty(tagIdList)) {
            List<QuestionTag> questionTags = tagIdList.stream()
                    .map(tagId -> new QuestionTag(tagId, questionId))
                    .collect(Collectors.toList());
            int count = questionTagMapper.batchInsert(questionTags);
            if (count != tagIdList.size()) {
                throw new AdminException("新增题目(标签)失败");
            }
        }

        QuestionDetail detail = new QuestionDetail();
        detail.setId(questionId);
        detail.setContent(form.getContent());
        questionDetailService.save(detail);
    }

    private void beforeCheck(QuestionForm form) {
        List<Integer> tagIdList = form.getTagIdList();
        if (ObjectUtil.isNotEmpty(tagIdList) && tagMapper.countById(tagIdList) != tagIdList.size()) {
            throw new AdminException("包含未知的标签");
        }
    }

    @Override
    @Transactional(rollbackFor = AdminException.class)
    public void update(Integer questionId, QuestionForm form) {
        beforeCheck(form);

        Question question = findById(questionId);

        question.setTitle(form.getTitle());
        question.setDifficulty(form.getDifficulty());
        if (questionMapper.update(question) != 1) {
            throw new AdminException("编辑题目失败");
        }

        questionTagMapper.deleteByQuestionId(questionId);
        List<QuestionTag> questionTags = form.getTagIdList().stream()
                .map(tagId -> new QuestionTag(tagId, questionId))
                .collect(Collectors.toList());
        questionTagMapper.batchInsert(questionTags);

        QuestionDetail detail = new QuestionDetail();
        detail.setId(questionId);
        detail.setContent(form.getContent());
        questionDetailService.updateById(detail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer questionId) {
        findById(questionId);
        questionMapper.deleteById(questionId);
        questionTagMapper.deleteByQuestionId(questionId);
        questionStatisticMapper.deleteById(questionId);
        questionDetailService.removeById(questionId);
    }

    @Override
    public QuestionDetailVO findOne(Integer questionId) {
        Question question = findById(questionId);
        QuestionDetailVO vo = ModelMapperUtil.map(question, QuestionDetailVO.class);

        QuestionDetail detail = questionDetailService.getById(questionId);
        vo.setQuestionContent(detail.getContent());

        List<Integer> questionIds = Collections.singletonList(questionId);
        QuestionWorker.build()
                .andThen(new QuestionStatisticWorker(questionIds))
                .andThen(new QuestionTagWorker(questionIds))
                .accept(vo);

        new QuestionLikeWorker(questionId).accept(vo);

        return vo;
    }

    public Question findById(Integer id) {
        return questionMapper.findById(id).orElseThrow(() -> new EntityException("题目不存在"));
    }


    /**
     * query question according to conditions. <br>
     * <p>
     * why find all from data source ? <br>
     * Firstly, when the number of question in database is less, the time consuming can be ignored. <br>
     * Secondly, when the number of question in database is large, we should try cache to reduce the query duration. Because the action that db execute page also need to scan all data. <br>
     * Mostly, the conditions is too much and too flexible !!!
     * </p>
     *
     * @param query {@link QuestionQuery}
     * @return all question.
     */
    @Override
    public PageVO<QuestionVO> listAll(QuestionQuery query) {
        Predicate<Integer> filters = QuestionFilter.build()
                .and(new UserStatusFilter(query))
                .and(new TagFilter(query));

        Question example = new Question();
        example.setDifficulty(query.getDifficulty());
        List<Integer> questionIds = questionMapper.findAllIdByExample(example)
                .stream()
                .filter(filters)
                .collect(Collectors.toList());

        long total = questionIds.size();
        List<Integer> neededIds = PageUtils.selectPage(questionIds, query);

        if (neededIds.isEmpty()) {
            return PageVO.of(total, new ArrayList<>());
        }

        Consumer<QuestionVO> workers = QuestionWorker.build()
                .andThen(new QuestionStatusWorker(questionIds))
                .andThen(new QuestionStatisticWorker(questionIds))
                .andThen(new QuestionTagWorker(questionIds));

        List<QuestionVO> vos = questionMapper.findAllById(neededIds)
                .stream()
                .map(question -> ModelMapperUtil.map(question, QuestionVO.class))
                .peek(workers)
                .collect(Collectors.toList());


        return PageVO.of(total, vos);
    }

    @Override
    public void like(Integer questionId) {
        findById(questionId);
        Integer userId = UserContext.getUserId();
        QuestionLike entity = new QuestionLike();
        entity.setQuestionId(questionId);
        entity.setUserId(userId);
        questionLikeMapper.insert(entity);
    }

    @Override
    public void unlike(Integer questionId) {
        Integer userId = UserContext.getUserId();
        questionLikeMapper.deleteByQuestionIdAndUserId(questionId, userId);
    }

    /**
     * Filter: User Status
     */
    private class UserStatusFilter implements QuestionFilter {

        private final QuestionQuery query;


        private Map<Integer, Integer> questionStatusMap;

        public UserStatusFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Integer questionId) {
            Integer status = query.getStatus();
            return ObjectUtils.isEmpty(status) || contains(questionId, query);
        }

        private boolean contains(Integer questionId, QuestionQuery query) {
            if (questionStatusMap == null) {
                Submission example = new Submission();
                example.setUserId(UserContext.getUserId());
                List<Submission> submissions = submissionMapper.findAllByExample(example);

                questionStatusMap = new HashMap<>(16);
                for (Submission submission : submissions) {
                    Integer qid = submission.getQuestionId();
                    Integer result = Math.max(submission.getResult(), questionStatusMap.getOrDefault(qid, 0));
                    questionStatusMap.put(qid, result);
                }
            }
            return questionStatusMap.getOrDefault(questionId, 0).equals(query.getStatus());
        }
    }

    /**
     * Filter: Question Tags
     */
    private class TagFilter implements QuestionFilter {

        private final QuestionQuery query;

        private Set<Integer> questionSet;

        private TagFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Integer questionId) {
            return ObjectUtils.isEmpty(query.getTags()) || contains(questionId, query);
        }

        private boolean contains(Integer questionId, QuestionQuery query) {
            if (questionSet == null) {
                questionSet = questionTagMapper.findQuestionByTagId(query.getTags());
            }
            return questionSet.contains(questionId);
        }
    }

    /**
     * Worker: process `status` in {@link QuestionVO}
     */
    private class QuestionStatusWorker implements QuestionWorker {

        private final List<Integer> questionIds;

        private Map<Integer, Integer> questionStatusMap;

        private QuestionStatusWorker(List<Integer> questionIds) {
            this.questionIds = questionIds;
        }

        @Override
        public void accept(QuestionVO vo) {
            Integer status = findStatus(vo.getQuestionId());
            vo.setStatus(status);
        }

        public Integer findStatus(Integer questionId) {
            if (questionStatusMap == null) {
                Integer userId = UserContext.getUserId();
                List<Submission> submissions = submissionMapper.findAllByUserIdAndQuestionIdList(userId, questionIds);
                questionStatusMap = new HashMap<>(16);
                for (Submission submission : submissions) {
                    Integer qid = submission.getQuestionId();
                    Integer result = Math.max(submission.getResult(), questionStatusMap.getOrDefault(qid, 0));
                    questionStatusMap.put(qid, result);
                }
            }
            return questionStatusMap.getOrDefault(questionId, 0);
        }
    }

    /**
     * Worker: Statistics
     */
    private class QuestionStatisticWorker implements QuestionWorker {

        private final List<Integer> questionIds;

        private List<QuestionStatistic> statistics;

        public QuestionStatisticWorker(List<Integer> questionIds) {
            this.questionIds = questionIds;
        }

        @Override
        public void accept(QuestionVO vo) {
            if (statistics == null) {
                statistics = questionStatisticMapper.findAllById(questionIds);
            }
            for (QuestionStatistic stat : statistics) {
                if (stat.getId().equals(vo.getQuestionId())) {
                    vo.setResolutionNum(stat.getSolution());
                    vo.setSuccessSubmission(stat.getSuccessSubmission());
                    vo.setFailSubmission(stat.getFailSubmission());
                    break;
                }
            }
        }
    }

    /**
     * Worker: Question Tags
     */
    private class QuestionTagWorker implements QuestionWorker {

        private final List<Integer> questionIds;

        private Map<Integer, Tag> tagMap;

        private Map<Integer, List<Integer>> questionTagMap;

        private QuestionTagWorker(List<Integer> questionIds) {
            this.questionIds = questionIds;
        }

        @Override
        public void accept(QuestionVO vo) {
            if (questionIds.size() <= 1) {
                List<Integer> ids = questionTagMapper.findAllByQuestionId(questionIds)
                        .stream()
                        .map(QuestionTag::getTagId)
                        .collect(Collectors.toList());

                List<Tag> tagList = tagMapper.findAllById(ids);
                vo.setTags(tagList);
                return;
            }
            if (tagMap == null) {
                Set<Integer> tagIdSet = new HashSet<>();
                Map<Integer, List<Integer>> map = new HashMap<>(questionIds.size());
                List<QuestionTag> questionTags = questionTagMapper.findAllByQuestionId(questionIds);

                for (QuestionTag questionTag : questionTags) {
                    Integer qid = questionTag.getQuestionId();
                    Integer tid = questionTag.getTagId();

                    List<Integer> list = map.get(qid);
                    if (list == null) {
                        List<Integer> ids = new ArrayList<>();
                        ids.add(tid);
                        map.put(qid, ids);
                    } else {
                        list.add(tid);
                    }
                    tagIdSet.add(tid);
                }
                this.questionTagMap = map;
                this.tagMap = tagMapper.findAllById(tagIdSet)
                        .stream()
                        .collect(Collectors.toMap(Tag::getId, tag -> tag));
            }

            List<Integer> tagIds = questionTagMap.get(vo.getQuestionId());
            if (tagIds != null) {
                List<Tag> list = new ArrayList<>(tagIds.size());
                for (Integer id : tagIds) {
                    list.add(tagMap.get(id));
                }
                vo.setTags(list);
            } else {
                vo.setTags(new ArrayList<>(0));
            }
        }
    }

    /**
     * Worker: Question Like
     */
    private class QuestionLikeWorker implements Consumer<QuestionDetailVO> {

        private final Integer questionId;

        private QuestionLikeWorker(Integer questionId) {
            this.questionId = questionId;
        }

        @Override
        public void accept(QuestionDetailVO vo) {
            Integer userId = UserContext.getUserId();
            List<QuestionLike> list = questionLikeMapper.findAllByQuestionId(Collections.singletonList(questionId));
            vo.setLikeNum(list.size());
            vo.setLiked(false);
            for (QuestionLike like : list) {
                if (like.getUserId().equals(userId)) {
                    vo.setLiked(true);
                }
            }
        }
    }

}
