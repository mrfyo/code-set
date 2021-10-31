package com.feyon.codeset.query;

import com.feyon.codeset.common.Pageable;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Feng Yong
 */
@Data
public class SolutionQuery implements Pageable {

    /**
     * page of table that start from <b>1</b>
     */
    private Integer page = 1;

    /**
     * size of table
     */
    private Integer size = 10;

    /**
     * the question id of solution.
     * @see com.feyon.codeset.entity.Question
     */
    @NotNull
    private Integer questionId;

    private Integer userId;

    /**
     * the tags of question.
     * @see com.feyon.codeset.entity.Tag
     */
    private List<Integer> tags;

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public Integer getSize() {
        return size;
    }
}
