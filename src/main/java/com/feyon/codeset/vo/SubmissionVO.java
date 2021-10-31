package com.feyon.codeset.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feng Yong
 */
@Data
public class SubmissionVO {
    private Integer id;

    private Integer result;

    private Integer questionId;

    private Integer userId;

    private Integer timeCost;

    private Integer memoryCost;

    private String language;

    private LocalDateTime createAt;

}
