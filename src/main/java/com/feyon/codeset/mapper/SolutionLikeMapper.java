package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.SolutionLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feng Yong
 */
@Mapper
public interface SolutionLikeMapper extends BaseMapper<SolutionLike> {
    List<SolutionLike> findAllBySolutionId(@Param("solutionIds") Iterable<Integer> solutionIds);

    int deleteBySolutionIdAndUserId(@Param("solutionId") Integer solutionId, @Param("userId") Integer userId);
}
