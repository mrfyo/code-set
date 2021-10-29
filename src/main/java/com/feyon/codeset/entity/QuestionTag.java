package com.feyon.codeset.entity;

import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class QuestionTag {

    private Integer id;

    private Integer questionId;

    private Integer tagId;

    public QuestionTag() {
    }

    public QuestionTag(Integer questionId, Integer tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }
}
