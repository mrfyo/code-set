package com.feyon.codeset.form;

import lombok.Data;

import java.util.List;

/**
 * @author Feng Yong
 */
@Data
public class QuestionForm {

    private Integer number;

    private String title;

    private Integer difficulty;

    private List<Integer> tags;
}
