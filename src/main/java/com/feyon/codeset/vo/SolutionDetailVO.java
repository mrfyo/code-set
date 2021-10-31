package com.feyon.codeset.vo;

import com.feyon.codeset.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Feng Yong
 */
@Data
public class SolutionDetailVO {

    private Integer solutionId;

    private String solutionTitle;

    private String solutionContent;

    private Integer questionId;

    private Integer userId;

    private LocalDateTime createAt;

    private List<Tag> tags;
}
