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

    private String summary;

    private Integer questionId;

    private Integer userId;

    private LocalDateTime createAt;

    public Solution() {
    }

    public Solution(Integer questionId, Integer userId) {
        this.questionId = questionId;
        this.userId = userId;
    }
}
