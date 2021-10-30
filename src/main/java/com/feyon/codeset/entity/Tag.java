package com.feyon.codeset.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Feng Yong
 */
@Data
public class Tag {
    private Integer id;

    @NotEmpty
    private String name;

}
