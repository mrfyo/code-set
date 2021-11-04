package com.feyon.codeset.service;


import org.springframework.lang.NonNull;

/**
 * @author Feng Yong
 */
public interface LikeService {

    /**
     * like a question.
     * @param resourceId question id.
     */
    void like(@NonNull Integer resourceId);

    /**
     * unlike or cancel a question.
     * @param resourceId question id
     */
    void unlike(@NonNull Integer resourceId);
}
