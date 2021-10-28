package com.feyon.codeset.service;

import com.feyon.codeset.entity.Tag;

import java.util.List;

/**
 * @author Feng Yong
 */
public interface QuestionTagService {

    /**
     * return all question tag.
     * @return all question tag.
     */
    List<Tag> listAll();
}
