package com.feyon.codeset.entity;

import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class QuestionTag {

    private Integer id;

    private Integer tagId;

    private Integer questionId;

    public QuestionTag() {
    }

    public QuestionTag(Integer tagId, Integer questionId) {
        this.tagId = tagId;
        this.questionId = questionId;
    }
}
