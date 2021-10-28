package com.feyon.codeset.vo;

import lombok.Data;

/**
 * View Object: Question
 * @author Feng Yong
 */
@Data
public class QuestionVO {
    /**
     * the situation that user solve this question. <br>
     * <b>0</b> means that user has not tried to solve. <br>
     * <b>1</b> means that user has tried but not pass. <br>
     * <b>2</b> means that user has passed
     */
    private Integer status;

    /**
     * id of question in table.
     */
    private Integer questionId;

    /**
     * serial number of the question.
     */
    private Integer questionNumber;

    /**
     * title of the question
     */
    private String questionTitle;

    /**
     * <b>1</b> means simple <br>
     * <b>2</b> means middle <br>
     * <b>3</b> means difficult <br>
     */
    private Integer questionDifficulty;

    /**
     * the number of resolution of this question. <br>
     * Note. this number is not belong to current user but all users.
     */
    private Long resolutionNum;

    /**
     * the passing rate of this question. <br>
     * Note. this rate is not belong to current user but all users.
     */
    private Double passRate;
}
