package com.feyon.codeset.service;


import org.springframework.lang.NonNull;

/**
 * @author Feng Yong
 */
public interface QuestionLikeService {

    /**
     * like a question.
     * @param questionId question id.
     */
    void like(@NonNull Integer questionId);

    /**
     * unlike or cancel a question.
     * @param questionId question id
     */
    void unlike(@NonNull Integer questionId);
}
