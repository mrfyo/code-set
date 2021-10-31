package com.feyon.codeset.mapper;

import com.feyon.codeset.common.Pageable;
import com.feyon.codeset.entity.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feng Yong
 */
@Mapper
public interface SolutionMapper extends BaseMapper<Solution> {

    long countByQuestionId(@Param("questionId") Integer questionId);

    long countByExample(Solution example);


    List<Solution> findAllByExample(@Param("example") Solution solution, @Param("page") Pageable pageable);

    default List<Solution> findAllByExample(Solution solution) {
        return findAllByExample(solution, null);
    }

    List<Integer> findAllIdByExample(Solution example);
}
