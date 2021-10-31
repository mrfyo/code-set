package com.feyon.codeset.form;

import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class SubmissionForm {

    private Integer userId;

    private Integer questionId;

    private Integer languageId;

    private String content;
}
