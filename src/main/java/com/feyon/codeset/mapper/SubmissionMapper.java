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

    List<Submission> findAllByExample(Submission example);

    List<Submission> findAllByUserIdAndQuestionIdList(@Param("userId") Integer userId, @Param("questionIds") Iterable<Integer> questionIds);
}
