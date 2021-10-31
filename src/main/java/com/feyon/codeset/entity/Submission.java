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

    /**
     * @see Question
     */
    private Integer questionId;

    /**
     * @see User
     */
    private Integer userId;

    /**
     * the time unit is ms.
     */
    private Integer timeCost;

    /**
     * the memory unit is KB.
     */
    private Integer memoryCost;

    /**
     * the submission language like Java, Python3.
     * @see SubmissionLanguage
     */
    private Integer languageId;

    private LocalDateTime createAt;

    public Submission() {
    }

}
