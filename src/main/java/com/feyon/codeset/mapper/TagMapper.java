package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feng Yong
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag>{

    long countById(@Param("ids") Iterable<Integer> ids);
}
