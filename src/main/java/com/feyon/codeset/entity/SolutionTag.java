package com.feyon.codeset.entity;

import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class SolutionTag {

    private Integer id;

    private Integer tagId;

    private Integer solutionId;

    public SolutionTag() {
    }

    public SolutionTag(Integer tagId, Integer solutionId) {
        this.tagId = tagId;
        this.solutionId = solutionId;
    }
}
