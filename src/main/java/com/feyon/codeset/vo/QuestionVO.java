package com.feyon.codeset.vo;

import com.feyon.codeset.entity.Tag;
import lombok.Data;

import java.util.List;

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
     * tags of question.
     */
    private List<Tag> tags;

    /**
     * the number of resolution of this question. <br>
     * Note. this number is not belong to current user but all users.
     */
    private Long resolutionNum;

    /**
     * the number of successful submissions. <br>
     * Note. this number is not belong to current user but all users.
     */
    private Long successSubmission;

    /**
     * the number of failure submissions. <br>
     * Note. this number is not belong to current user but all users.
     */
    private Long failSubmission;

    /**
     * the number meet how many users liked.
     */
    private Integer likeNum;
}
