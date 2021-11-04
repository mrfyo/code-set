package com.feyon.codeset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.feyon.codeset.entity.*;
import com.feyon.codeset.exception.AdminException;
import com.feyon.codeset.exception.EntityException;
import com.feyon.codeset.form.SolutionForm;
import com.feyon.codeset.mapper.SolutionDetailMapper;
import com.feyon.codeset.mapper.SolutionMapper;
import com.feyon.codeset.mapper.SolutionTagMapper;
import com.feyon.codeset.mapper.TagMapper;
import com.feyon.codeset.query.SolutionQuery;
import com.feyon.codeset.service.SolutionService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.util.PageUtils;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.SolutionDetailVO;
import com.feyon.codeset.vo.SolutionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
@RequiredArgsConstructor
public class SolutionServiceImpl implements SolutionService {

    private final static int SUMMARY_MAX_SIZE = 64;

    private final SolutionMapper solutionMapper;

    private final SolutionTagMapper solutionTagMapper;

    private final SolutionDetailMapper solutionDetailMapper;

    private final TagMapper tagMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SolutionForm form) {
        beforeCheck(form);

        Solution solution = new Solution();
        solution.setUserId(form.getUserId());
        solution.setQuestionId(form.getQuestionId());
        solution.setTitle(form.getTitle());
        solution.setSummary(generateSummary(form.getContent()));
        solution.setCreateAt(LocalDateTime.now());
        solutionMapper.insert(solution);

        if (ObjectUtil.isNotEmpty(form.getTagIds())) {
            List<SolutionTag> solutionTags = form.getTagIds().stream()
                    .map(tagId -> new SolutionTag(tagId, solution.getId()))
                    .collect(Collectors.toList());
            solutionTagMapper.batchInsert(solutionTags);
        }

        SolutionDetail detail = new SolutionDetail();
        detail.setId(solution.getId());
        detail.setContent(form.getContent());
        solutionDetailMapper.insert(detail);
    }

    private void beforeCheck(SolutionForm form) {
        List<Integer> tagIdList = form.getTagIds();
        if (ObjectUtil.isNotEmpty(tagIdList) && tagMapper.countById(tagIdList) != tagIdList.size()) {
            throw new AdminException("包含未知的标签");
        }
    }

    @Override
    public void update(Integer solutionId, SolutionForm form) {
        beforeCheck(form);

        Solution solution = findById(solutionId);
        solution.setTitle(form.getTitle());
        solution.setSummary(generateSummary(form.getContent()));
        solution.setQuestionId(form.getQuestionId());
        solutionMapper.update(solution);

        solutionTagMapper.deleteBySolutionId(solutionId);
        List<SolutionTag> solutionTags = form.getTagIds().stream()
                .map(tagId -> new SolutionTag(tagId, solution.getId()))
                .collect(Collectors.toList());
        solutionTagMapper.batchInsert(solutionTags);

        SolutionDetail detail = new SolutionDetail();
        detail.setId(solution.getId());
        detail.setContent(form.getContent());
        solutionDetailMapper.update(detail);

    }

    private String generateSummary(String content) {
        return content.substring(0, Math.min(SUMMARY_MAX_SIZE, content.length()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer solutionId) {
        findById(solutionId);
        solutionMapper.deleteById(solutionId);
        solutionDetailMapper.deleteById(solutionId);
        solutionTagMapper.deleteBySolutionId(solutionId);
    }

    @Override
    public SolutionDetailVO findOne(Integer solutionId) {
        Solution solution = findById(solutionId);
        SolutionDetail detail = solutionDetailMapper.findById(solutionId).orElse(new SolutionDetail());

        SolutionDetailVO vo = ModelMapperUtil.map(solution, SolutionDetailVO.class);
        vo.setSolutionContent(detail.getContent());

        Consumer<SolutionVO> workers = new SolutionTagWorker(List.of(solutionId));
        workers.accept(vo);
        return vo;
    }

    public Solution findById(Integer id) {
        return solutionMapper.findById(id).orElseThrow(() -> new EntityException("题解不存在"));
    }

    @Override
    public PageVO<SolutionVO> listAll(SolutionQuery query) {
        Predicate<Integer> filters = new SolutionTagFilter(query);

        Solution example = new Solution();
        example.setQuestionId(query.getQuestionId());
        example.setUserId(query.getUserId());

        List<Integer> solutionIdList = solutionMapper.findAllIdByExample(example)
                .stream()
                .filter(filters)
                .collect(Collectors.toList());

        long total = solutionIdList.size();
        List<Integer> neededSolutionIds = PageUtils.selectPage(solutionIdList, query);

        Consumer<SolutionVO> workers = new SolutionTagWorker(neededSolutionIds);

        List<SolutionVO> vos = solutionMapper.findAllById(neededSolutionIds)
                .stream()
                .map(solution -> ModelMapperUtil.map(solution, SolutionVO.class))
                .peek(workers)
                .collect(Collectors.toList());

        return PageVO.of(total, vos);
    }

    /**
     * Filter: tag
     */
    public class SolutionTagFilter implements Predicate<Integer> {
        private final SolutionQuery query;

        private Set<Integer> solutionIdSet;

        public SolutionTagFilter(SolutionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Integer solutionId) {
            return ObjectUtils.isEmpty(query.getTags()) || contains(solutionId);
        }

        private boolean contains(Integer solutionId) {
            if (solutionIdSet == null) {
                solutionIdSet = solutionTagMapper.findAllByTagId(query.getTags())
                        .stream()
                        .map(SolutionTag::getSolutionId)
                        .collect(Collectors.toSet());
            }
            return solutionIdSet.contains(solutionId);
        }
    }

    /**
     * Worker: Tag
     */
    public class SolutionTagWorker implements Consumer<SolutionVO> {

        private final List<Integer> solutionIds;

        private Map<Integer, List<Integer>> solutionTagMap;

        private List<Tag> tags;

        public SolutionTagWorker(List<Integer> solutionIds) {
            this.solutionIds = solutionIds;
        }

        @Override
        public void accept(SolutionVO solutionVO) {
            if (tags == null) {
                List<SolutionTag> solutionTags = solutionTagMapper.findAllBySolutionId(solutionIds);
                Set<Integer> tagIds = new HashSet<>();
                Map<Integer, List<Integer>> map = new HashMap<>(16);

                for (SolutionTag solutionTag : solutionTags) {
                    Integer tagId = solutionTag.getTagId();
                    Integer solutionId = solutionTag.getSolutionId();
                    tagIds.add(tagId);
                    List<Integer> list = map.get(solutionId);
                    if (list == null) {
                        ArrayList<Integer> newList = new ArrayList<>();
                        newList.add(tagId);
                        map.put(solutionId, newList);
                    } else {
                        list.add(tagId);
                    }
                }
                tags = tagMapper.findAllById(tagIds);
                solutionTagMap = map;
            }

            Integer id = solutionVO.getSolutionId();
            if (solutionTagMap.containsKey(id)) {
                List<Integer> tagIds = solutionTagMap.get(id);
                List<Tag> tagList = new ArrayList<>(tagIds.size());
                for (Integer tagId : solutionTagMap.get(id)) {
                    for (Tag tag : tags) {
                        if (tag.getId().equals(tagId)) {
                            tagList.add(tag);
                        }
                    }
                }
                solutionVO.setTags(tagList);
            }
        }
    }

}
