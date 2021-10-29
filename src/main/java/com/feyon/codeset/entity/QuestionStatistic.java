package com.feyon.codeset.entity;

import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class QuestionStatistic {

    private Integer id;

    private Long solution;

    private Long successSubmission;

    private Long failSubmission;

}
