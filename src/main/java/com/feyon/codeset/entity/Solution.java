package com.feyon.codeset.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feng Yong
 */
@Data
public class Solution {
    private Integer id;

    private String title;

    private Integer questionId;

    private Integer userId;

    private LocalDateTime createAt;
}
