package com.feyon.codeset.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feng Yong
 */
@Data
public class Submission {

    private Integer id;

    /**
     * <b>1</b> means success.
     * <b>2</b> means fail.
     */
    private Integer result;

    private Integer questionId;

    private Integer userId;

    private LocalDateTime createAt;

    public Submission() {
    }

    public Submission(Integer id, Integer result, Integer questionId, Integer userId) {
        this.id = id;
        this.result = result;
        this.questionId = questionId;
        this.userId = userId;
    }

    public Submission(Integer result, Integer questionId, Integer userId) {
        this.result = result;
        this.questionId = questionId;
        this.userId = userId;
    }
}
