package com.feyon.codeset.service;

import com.feyon.codeset.form.QuestionForm;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionVO;

import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Service: Question
 *
 * @author Feng Yong
 */
public interface QuestionService {

    interface QuestionFilter extends Predicate<Integer> {
        /**
         * filter builder
         *
         * @return all pass filter.
         */
        static Predicate<Integer> build() {
            return question -> true;
        }
    }

    interface QuestionWorker extends Consumer<QuestionVO> {
        /**
         * worker builder
         *
         * @return identity worker.
         */
        static Consumer<QuestionVO> build() {
            return questionVO -> {
            };
        }
    }

    /**
     * return all {@link QuestionVO} that meet all conditions from user.
     *
     * @param query {@link QuestionQuery}
     * @return all question that meet all conditions from user.
     */
    PageVO<QuestionVO> listAll(QuestionQuery query);

    /**
     * save a question.
     *
     * @param form question form.
     */
    void save(QuestionForm form);

    /**
     * update a question.
     *
     * @param questionId question's id.
     * @param form       question form.
     */
    void update(Integer questionId, QuestionForm form);

    /**
     * remove a question.
     *
     * @param questionId question's id.
     */
    void remove(Integer questionId);
}
