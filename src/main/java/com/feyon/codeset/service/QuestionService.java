package com.feyon.codeset.service;

import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionVO;


/**
 * Service: Question
 * @author Feng Yong
 */
public interface QuestionService {

    interface PassRateQueryer {
        /**
         * return the pass rate of question
         * @param questionId question's id
         * @return the pass rate of question
         */
        double query(Integer questionId);
    }

    /**
     * return all {@link QuestionVO} that meet all conditions from user.
     * @param query {@link QuestionQuery}
     * @return all question that meet all conditions from user.
     */
    PageVO<QuestionVO> listAll(QuestionQuery query);



}
