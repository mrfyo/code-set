package com.feyon.codeset.service;

import com.feyon.codeset.entity.Question;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionVO;

import java.util.function.BiPredicate;
import java.util.function.Predicate;


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

    interface QuestionFilter extends Predicate<Question> {
        /**
         * filter builder
         * @return all pass filter.
         */
        static Predicate<Question> build() {
            return question -> true;
        }
    }

    /**
     * return all {@link QuestionVO} that meet all conditions from user.
     * @param query {@link QuestionQuery}
     * @return all question that meet all conditions from user.
     */
    PageVO<QuestionVO> listAll(QuestionQuery query);



}
