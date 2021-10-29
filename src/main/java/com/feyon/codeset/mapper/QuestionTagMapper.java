package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.QuestionTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feng Yong
 */
@Mapper
public interface QuestionTagMapper extends BaseMapper<QuestionTag> {

    int batchInsert(@Param("tags") List<QuestionTag> tags);

    List<QuestionTag> findByQuestionId(@Param("questionId") Integer questionId);

    int deleteByQuestionId(@Param("questionId") Integer questionId);

    List<QuestionTag> findByTagId(@Param("tagId") Integer tagId);

    int deleteByTagId(@Param("tagId") Integer tagId);
}
