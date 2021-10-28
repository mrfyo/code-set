package com.feyon.codeset.entity;

import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class UserQuestion {

    private Integer id;

    /**
     * id of user.
     */
    private Integer userId;

    /**
     * id of question.
     */
    private Integer questionId;

    /**
     * the situation that user solve this question. <br>
     * <b>0</b> means that user has not tried to solve. <br>
     * <b>1</b> means that user has tried but not pass. <br>
     * <b>2</b> means that user has passed
     */
    private Integer status;
}
