package com.feyon.codeset.service.impl;

import com.feyon.codeset.entity.QuestionDetail;
import com.feyon.codeset.exception.EntityException;
import com.feyon.codeset.mapper.QuestionDetailMapper;
import com.feyon.codeset.service.QuestionDetailService;
import org.springframework.stereotype.Service;

/**
 * @author Feng Yong
 */
@Service
public class QuestionDetailServiceImpl implements QuestionDetailService {

    private final QuestionDetailMapper questionDetailMapper;

    public QuestionDetailServiceImpl(QuestionDetailMapper questionDetailMapper) {
        this.questionDetailMapper = questionDetailMapper;
    }


    @Override
    public void save(QuestionDetail entity) {
        questionDetailMapper.insert(entity);
    }

    @Override
    public void removeById(Integer id) {
        if(questionDetailMapper.deleteById(id) != 1) {
            throw new EntityException("问题详情不存在");
        }
    }

    @Override
    public void updateById(QuestionDetail entity) {
        if(questionDetailMapper.update(entity) != 1) {
            throw new EntityException("问题详情不存在");
        }
    }

    @Override
    public QuestionDetail getById(Integer id) {
        return questionDetailMapper.findById(id).orElseThrow(() -> new EntityException("问题内容不存在"));
    }
}
