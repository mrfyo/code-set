package com.feyon.codeset.service;

import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.vo.QuestionVO;

import java.util.List;
import java.util.stream.Stream;

/**
 * Service: Question
 * @author Feng Yong
 */
public interface QuestionService {
    /**
     * return all {@link QuestionVO} that meet all conditions from user.
     * @param query {@link QuestionQuery}
     * @return all question that meet all conditions from user.
     */
    List<QuestionVO> listAll(QuestionQuery query);
}
