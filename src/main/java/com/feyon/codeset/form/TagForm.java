package com.feyon.codeset.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Feng Yong
 */
@Data
public class TagForm {
    @NotEmpty
    private String name;
}
