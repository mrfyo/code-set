package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.QuestionLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feng Yong
 */
@Mapper
public interface QuestionLikeMapper extends BaseMapper<QuestionLike>{

    List<QuestionLike> findAllByQuestionId(@Param("questionIds") Iterable<Integer> questionIds);

    int deleteByQuestionIdAndUserId(@Param("questionId") Integer questionId, @Param("userId") Integer userId);
}
