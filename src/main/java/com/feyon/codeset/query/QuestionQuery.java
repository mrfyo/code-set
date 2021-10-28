package com.feyon.codeset.query;

import com.feyon.codeset.common.Pageable;
import lombok.Data;

import java.util.List;

/**
 * @author Feng Yong
 */
@Data
public class QuestionQuery implements Pageable {
    /**
     * page of table that start from <b>1</b>
     */
    private Integer page;

    /**
     * size of table
     */
    private Integer size;

    /**
     * the difficulty of question.
     * @see com.feyon.codeset.entity.Question
     */
    private Integer difficulty;

    /**
     * the status that user solve question.
     * @see com.feyon.codeset.entity.UserQuestion
     */
    private Integer status;

    /**
     * the tags of question.
     * @see com.feyon.codeset.entity.Tag
     */
    private List<Integer> tags;
}

