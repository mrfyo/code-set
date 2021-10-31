package com.feyon.codeset.query;

import com.feyon.codeset.common.Pageable;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Feng Yong
 */
@Data
public class QuestionQuery implements Pageable {
    /**
     * page of table that start from <b>1</b>
     */
    @Min(1)
    private Integer page = 1;

    /**
     * size of table
     */
    @Range(min = 0, max = 100)
    private Integer size = 20;

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

