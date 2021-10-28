package com.feyon.codeset.service.impl;

import com.feyon.codeset.entity.Question;
import com.feyon.codeset.mapper.QuestionMapper;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.service.QuestionService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.vo.QuestionVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;


    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    /**
     * query question according to conditions. <br>
     * <p>
     *     why find all from data source ? <br>
     *     Firstly, when the number of question in database is less, the time consuming can be ignored. <br>
     *     Secondly, when the number of question in database is large, we should try cache to reduce the query duration. Because the action that db execute page also need to scan all data. <br>
     *     Mostly, the conditions is too much and too flexible !!!
     * </p>
     * @param query {@link QuestionQuery}
     * @return all question.
     */
    @Override
    public List<QuestionVO> listAll(QuestionQuery query) {
        Integer userId = 1;
        return listAll().stream()
                .filter(question -> ObjectUtils.isEmpty(query.getDifficulty()) || question.getDifficulty().equals(query.getDifficulty()))
                .filter(question -> ObjectUtils.isEmpty(query.getStatus()) || questionMapper.holdStatus(question.getId(), userId, query.getStatus()))
                .filter(question -> ObjectUtils.isEmpty(query.getTags()) || questionMapper.containsTag(question.getId(), query.getTags()))
                .map(question -> ModelMapperUtil.map(question, QuestionVO.class))
                .skip(query.getOffset())
                .limit(query.getLimit())
                .collect(Collectors.toList());
    }

    public List<Question> listAll() {
        return questionMapper.findAll();
    }
}
