package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.SolutionTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Feng Yong
 */
@Mapper
public interface SolutionTagMapper extends BaseMapper<SolutionTag> {

    int batchInsert(@Param("tags") List<SolutionTag> tags);

    List<SolutionTag> findBySolutionId(@Param("solutionId") Integer solutionId);

    List<SolutionTag> findAllBySolutionId(@Param("solutionIds") Iterable<Integer> solutionIds);

    int deleteBySolutionId(@Param("solutionId") Integer solutionId);

    List<SolutionTag> findByTagId(@Param("tagId") Integer tagId);

    long countByTagId(@Param("tagId") Integer tagId);

    List<SolutionTag> findAllByTagId(@Param("tagIds") Iterable<Integer> tagIds);

    Set<Integer> findSolutionByTagId(@Param("tagIds") Iterable<Integer> tagIds);

    int deleteByTagId(@Param("tagId") Integer tagId);
}
