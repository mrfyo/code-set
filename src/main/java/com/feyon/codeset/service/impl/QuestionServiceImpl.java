package com.feyon.codeset.service.impl;

import com.feyon.codeset.entity.Question;
import com.feyon.codeset.entity.Submission;
import com.feyon.codeset.entity.UserQuestion;
import com.feyon.codeset.mapper.QuestionMapper;
import com.feyon.codeset.mapper.SolutionMapper;
import com.feyon.codeset.mapper.SubmissionMapper;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.service.QuestionService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    private final SolutionMapper solutionMapper;

    private final PassRateHandler passRateHandler;


    public QuestionServiceImpl(QuestionMapper questionMapper, SolutionMapper solutionMapper, PassRateHandler passRateHandler) {
        this.questionMapper = questionMapper;
        this.solutionMapper = solutionMapper;
        this.passRateHandler = passRateHandler;
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
        Predicate<Question> filters = QuestionFilter.build()
                .and(new DifficultyFilter(query))
                .and(new UserStatusFilter(query))
                .and(new TagFilter(query));

        Consumer<List<QuestionVO>> workers = QuestionWorker.build()
                .andThen(new QuestionStatusWorker(query))
                .andThen(new QuestionPassRateWorker(passRateHandler))
                .andThen(new SolutionCountWorker());


        List<Question> questions = listAll().stream().filter(filters).collect(Collectors.toList());
        List<QuestionVO> vos = new ArrayList<>(query.getLimit());
        int end = Math.min(query.getOffset() + query.getLimit(), questions.size());
        for (int i = query.getOffset(); i < end; i++) {
            vos.add(ModelMapperUtil.map(questions.get(i), QuestionVO.class));
        }
        workers.accept(vos);
        return PageVO.of(questions.size(), vos);
    }

    public List<Question> listAll() {
        return questionMapper.findAll();
    }

    /**
     * Filter: Question.Difficulty
     */
    private static class DifficultyFilter implements QuestionFilter {

        private final QuestionQuery query;

        private DifficultyFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Question question) {
            return ObjectUtils.isEmpty(query.getDifficulty()) || question.getDifficulty().equals(query.getDifficulty());
        }
    }

    /**
     * Filter: User Status
     */
    private class UserStatusFilter implements QuestionFilter {

        private final QuestionQuery query;

        private Set<Integer> questionSet;

        private UserStatusFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Question question) {
            return ObjectUtils.isEmpty(query.getStatus()) || contains(question, query);
        }

        private boolean contains(Question question, QuestionQuery query) {
            if (questionSet == null) {
                questionSet = questionMapper.listAllForUser(1, query.getStatus())
                        .stream()
                        .map(UserQuestion::getQuestionId)
                        .collect(Collectors.toSet());
            }
            return questionSet.contains(question.getId());
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
        public boolean test(Question question) {
            return ObjectUtils.isEmpty(query.getTags()) || contains(question, query);
        }

        private boolean contains(Question question, QuestionQuery query) {
            if (questionSet == null) {
                questionSet = questionMapper.listAllForTag(query.getTags());
            }
            return questionSet.contains(question.getId());
        }
    }

    /**
     * Worker: process `status` in {@link QuestionVO}
     */
    public class QuestionStatusWorker implements QuestionWorker {

        private final QuestionQuery query;

        private Map<Integer, Integer> questionStatusMap;

        public QuestionStatusWorker(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public void accept(List<QuestionVO> vos) {
            for (QuestionVO vo : vos) {
                Integer status = ObjectUtils.isEmpty(query.getStatus()) ? findStatus(vo.getQuestionId()) : query.getStatus();
                vo.setStatus(status);
            }
        }

        public Integer findStatus(Integer questionId) {
            if (questionStatusMap == null) {
                questionStatusMap = questionMapper.listAllForUser(1, null)
                        .stream()
                        .collect(Collectors.toMap(UserQuestion::getQuestionId, UserQuestion::getStatus));
            }
            return questionStatusMap.get(questionId);
        }
    }

    /**
     * Worker: Pass Rate
     */
    public static class QuestionPassRateWorker implements QuestionWorker {

        final PassRateHandler handler;

        public QuestionPassRateWorker(PassRateHandler handler) {
            this.handler = handler;
        }


        @Override
        public void accept(List<QuestionVO> vos) {
            for (QuestionVO vo : vos) {
                vo.setPassRate(handler.handle(vo.getQuestionId()));
            }
        }
    }

    public class SolutionCountWorker implements QuestionWorker {
        @Override
        public void accept(List<QuestionVO> vos) {
            for (QuestionVO vo : vos) {
                long num = solutionMapper.countByQuestionId(vo.getQuestionId());
                vo.setResolutionNum(num);
            }
        }
    }


    /**
     * the pass rate of question for user.
     */
    @Component
    public static class SimplePassRateHandler implements PassRateHandler {

        private final SubmissionMapper submissionMapper;

        public SimplePassRateHandler(SubmissionMapper submissionMapper) {
            this.submissionMapper = submissionMapper;
        }

        @Override
        public double handle(Integer questionId) {
            long total = submissionMapper.countByExample(new Submission(null, questionId, null));
            if (total == 0) {
                return 0;
            }
            long pass = submissionMapper.countByExample(new Submission(1, questionId, null));
            long result = (pass * 1000 / total);
            return result / 10.0;
        }
    }
}
