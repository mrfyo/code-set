package com.feyon.codeset.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Feng Yong
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
