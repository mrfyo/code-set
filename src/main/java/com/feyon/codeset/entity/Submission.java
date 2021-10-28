package com.feyon.codeset.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feng Yong
 */
@Data
public class Submission {

    private Integer id;

    private Integer result;

    private Integer questionId;

    private LocalDateTime createAt;

}
