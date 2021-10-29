package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Feng Yong
 */
@Mapper
public interface SolutionMapper extends BaseMapper<Solution> {

    long countByQuestionId(@Param("questionId") Integer questionId);

    long countByExample(Solution example);
}
