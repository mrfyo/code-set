package com.feyon.codeset.mapper;

import com.feyon.codeset.common.Pageable;
import com.feyon.codeset.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ORM: Question
 *
 * @author Feng Yong
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * paging query.
     *
     * @param pageable {@link Pageable}
     * @return question list.
     */
    List<Question> listAllByPage(Pageable pageable);

    /**
     * return the question is or not contains any tag.
     *
     * @param questionId question's id.
     * @param tagIds     the list of id of list.
     * @return the question is or not contains any tag.
     */
    boolean containsTag(@Param("questionId") Integer questionId, @Param("tagIds") List<Integer> tagIds);

    /**
     * return the question is or not has same status for this user.
     *
     * @param questionId question's id.
     * @param userId     user' id
     * @param status user's status
     * @return the question is or not has same status for this user.
     */
    boolean holdStatus(@Param("questionId") Integer questionId, @Param("userId") Integer userId, @Param("status") Integer status);
}
