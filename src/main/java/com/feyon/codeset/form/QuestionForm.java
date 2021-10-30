package com.feyon.codeset.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Feng Yong
 */
@Data
public class QuestionForm {

    @NotEmpty
    private String title;

    @Range(min = 1, max = 3)
    private Integer difficulty;

    private List<Integer> tagIdList;
}
