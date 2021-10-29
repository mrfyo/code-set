package com.feyon.codeset.service.impl;

import com.feyon.codeset.entity.QuestionTag;
import com.feyon.codeset.mapper.QuestionTagMapper;
import com.feyon.codeset.mapper.TagMapper;
import com.feyon.codeset.service.TagService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.vo.TagVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    private final QuestionTagMapper questionTagMapper;

    public TagServiceImpl(TagMapper tagMapper, QuestionTagMapper questionTagMapper) {
        this.tagMapper = tagMapper;
        this.questionTagMapper = questionTagMapper;
    }

    @Override
    public List<TagVO> findAll() {
        Map<Integer, Long> collect = questionTagMapper.findAll()
                .stream()
                .collect(Collectors.groupingBy(QuestionTag::getTagId, Collectors.counting()));

        return tagMapper.findAllById(collect.keySet())
                .stream()
                .map(tag -> ModelMapperUtil.map(tag, TagVO.class))
                .peek(tagVO -> tagVO.setQuestionNum(collect.get(tagVO.getTagId())))
                .collect(Collectors.toList());
    }
}
