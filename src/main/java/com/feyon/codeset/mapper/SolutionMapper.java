package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Solution;
import com.feyon.codeset.entity.Submission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Feng Yong
 */
@Mapper
public interface SolutionMapper extends BaseMapper<Solution> {

    long countByExample(Solution example);
}
