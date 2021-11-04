package com.feyon.codeset.mapper;

import com.feyon.codeset.common.Pageable;
import com.feyon.codeset.entity.Question;
import com.feyon.codeset.entity.UserQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * ORM: Question
 *
 * @author Feng Yong
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    boolean existsById(@Param("id") Integer id);

    Question findByNumber(@Param("number") Integer number);

    Integer nextNumber();

    List<Question> findByExample(Question example);

    List<Integer> findAllIdByExample(Question example);

    List<UserQuestion> listAllForUser(@Param("userId") Integer userId, @Param("status") @Nullable Integer status);


}
