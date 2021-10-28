package com.feyon.codeset.service.impl;

import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.mapper.TagMapper;
import com.feyon.codeset.service.QuestionTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Feng Yong
 */
@Service
public class QuestionTagServiceImpl implements QuestionTagService {

    private final TagMapper tagMapper;

    public QuestionTagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> listAll() {
        return tagMapper.findAll();
    }
}
