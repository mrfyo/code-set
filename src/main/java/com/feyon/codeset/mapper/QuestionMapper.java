package com.feyon.codeset.mapper;

import com.feyon.codeset.common.Pageable;
import com.feyon.codeset.entity.Question;
import com.feyon.codeset.entity.UserQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

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

    List<UserQuestion> listAllForUser(@Param("userId")Integer userId, @Param("status") @Nullable Integer status);

    Set<Integer> listAllForTag(@Param("tagIds") List<Integer> tagIds);
}
