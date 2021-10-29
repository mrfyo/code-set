package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Submission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feng Yong
 */
@Mapper
public interface SubmissionMapper extends BaseMapper<Submission> {

    long countByExample(Submission example);

    List<Submission> findAllByExample(Submission example);
}
