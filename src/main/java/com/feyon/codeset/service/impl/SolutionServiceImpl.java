package com.feyon.codeset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.feyon.codeset.entity.Solution;
import com.feyon.codeset.entity.SolutionDetail;
import com.feyon.codeset.entity.SolutionTag;
import com.feyon.codeset.entity.Tag;
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
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.SolutionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
public class SolutionServiceImpl implements SolutionService {

    private final static int SUMMARY_MAX_SIZE = 64;

    private final SolutionMapper solutionMapper;

    private final SolutionTagMapper solutionTagMapper;

    private final SolutionDetailMapper solutionDetailMapper;

    private final TagMapper tagMapper;

    public SolutionServiceImpl(SolutionMapper solutionMapper,
                               SolutionTagMapper solutionTagMapper,
                               SolutionDetailMapper solutionDetailMapper,
                               TagMapper tagMapper) {
        this.solutionMapper = solutionMapper;
        this.solutionTagMapper = solutionTagMapper;
        this.solutionDetailMapper = solutionDetailMapper;
        this.tagMapper = tagMapper;
    }


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

        if(ObjectUtil.isNotEmpty(form.getTagIds())) {
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

    public Solution findById(Integer id) {
        return solutionMapper.findById(id).orElseThrow(() -> new EntityException("题解不存在"));
    }

    @Override
    public PageVO<SolutionVO> listAll(SolutionQuery query) {
        List<Integer> tags = query.getTags();
        if (ObjectUtils.isEmpty(tags)) {
            List<Solution> list = findAll(query);
            List<SolutionVO> vos = ModelMapperUtil.mapList(list, SolutionVO.class);
            return PageVO.of(vos.size(), vos);
        }
        // all solution id that meets tag
        List<SolutionTag> solutionTagList = solutionTagMapper.findAllByTagId(tags);
        Set<Integer> solutionIdSet = new HashSet<>();
        for (SolutionTag solutionTag : solutionTagList) {
            solutionIdSet.add(solutionTag.getSolutionId());
        }

        // all solutions for this question.
        Solution example = new Solution();
        example.setQuestionId(query.getQuestionId());

        // all solution ids that meet tags and question.
        List<Integer> ids = solutionMapper.findAllIdByExample(example)
                .stream()
                .filter(solutionIdSet::contains)
                .collect(Collectors.toList());

        long total = ids.size();

        // solution id set actually needed
        Set<Integer> activeSolutionIds = new HashSet<>();
        int start = Math.max(0, query.getOffset());
        int end = Math.min(query.getOffset() + query.getLimit(), ids.size());
        for (int i = start; i < end; i++) {
            activeSolutionIds.add(ids.get(i));
        }

        if (activeSolutionIds.isEmpty()) {
            return PageVO.of(total, List.of());
        }

        // fetch full solution.
        List<SolutionVO> vos = new ArrayList<>(activeSolutionIds.size());
        Map<Integer, SolutionVO> voMap = solutionMapper.findAllById(ids)
                .stream()
                .map(solution -> ModelMapperUtil.map(solution, SolutionVO.class))
                .peek(solutionVO -> solutionVO.setTags(new ArrayList<>()))
                .peek(vos::add)
                .collect(Collectors.toMap(SolutionVO::getSolutionId, solutionVO -> solutionVO));

        // filter active tag id set.
        List<Integer> tagIds = solutionTagList.stream()
                .filter(solutionTag -> activeSolutionIds.contains(solutionTag.getSolutionId()))
                .map(SolutionTag::getTagId)
                .collect(Collectors.toList());

        Map<Integer, Tag> tagMap = tagMapper.findAllById(tagIds)
                .stream()
                .collect(Collectors.toMap(Tag::getId, tag -> tag));

        solutionTagList.forEach(solutionTag -> {
            Integer tagId = solutionTag.getTagId();
            Integer solutionId = solutionTag.getSolutionId();
            voMap.get(solutionId).getTags().add(tagMap.get(tagId));
        });

        return PageVO.of(total, vos);
    }

    public List<Solution> findAll(SolutionQuery query) {
        Solution example = new Solution();
        example.setQuestionId(query.getQuestionId());
        return solutionMapper.findAllByExample(example, query);
    }
}
