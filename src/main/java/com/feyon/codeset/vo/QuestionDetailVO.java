package com.feyon.codeset.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Feng Yong
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionDetailVO extends QuestionVO {

    /**
     * the number meet how many users liked.
     */
    private Integer likeNum;

    /**
     * is or not like this question.
     */
    private Boolean liked;

    private String questionContent;
}
