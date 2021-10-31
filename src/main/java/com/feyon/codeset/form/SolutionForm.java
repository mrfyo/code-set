package com.feyon.codeset.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that can used to insert or update an {@link com.feyon.codeset.entity.Solution}
 * @author Feng Yong
 */
@Data
public class SolutionForm {

    private Integer userId;

    /**
     * the id of question
     */
    private Integer questionId;

    /**
     * the title of solution.
     */
    @NotBlank
    private String title;

    /**
     * the max size of solution content is <b>32</b>KB
     */
    @Size(max = 1<<15)
    @NotBlank
    private String content;

    /**
     * the tags of solution.
     */
    private List<Integer> tagIds = new ArrayList<>();
}
