package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.QuestionTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Feng Yong
 */
@Mapper
public interface QuestionTagMapper extends BaseMapper<QuestionTag> {

    int batchInsert(@Param("tags") List<QuestionTag> tags);

    List<QuestionTag> findByQuestionId(@Param("questionId") Integer questionId);

    List<QuestionTag> findAllByQuestionId(@Param("questionIds") Iterable<Integer> questionIds);

    int deleteByQuestionId(@Param("questionId") Integer questionId);

    List<QuestionTag> findByTagId(@Param("tagId") Integer tagId);

    long countByTagId(@Param("tagId") Integer tagId);

    Set<Integer> findQuestionByTagId(@Param("tagIds") Iterable<Integer> tagIds);

    int deleteByTagId(@Param("tagId") Integer tagId);
}
