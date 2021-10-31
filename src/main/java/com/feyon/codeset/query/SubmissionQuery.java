package com.feyon.codeset.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Feng Yong
 */
@Data
public class SubmissionQuery {

    @NotNull
    private Integer questionId;

    @NotNull
    private Integer userId;

    private Integer languageId;
}
