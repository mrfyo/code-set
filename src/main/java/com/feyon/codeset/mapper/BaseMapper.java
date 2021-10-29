package com.feyon.codeset.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Feng Yong
 */
public interface BaseMapper<T> {

    /**
     * try insert an entity.
     * @param entity an entity object
     * @return result that trying to save an entity.
     */
    int insert(@NonNull T entity);

    /**
     * try update an entity according to id of entity.
     * @param entity need to save and provide valid primary key.
     * @return updating result.
     */
    int update(@NonNull T entity);

    /**
     * return the result that trying to delete an entity by id.
     * @param id entity's id
     * @return if deleting success, return 1; else return 0
     */
    int deleteById(Integer id);

    /**
     * return entity object according to id;
     * @param id entity's id
     * @return entity object or null
     */
    @Nullable
    T findById(Integer id);

    /**
     * return all entity.
     * @return all entity.
     */
    List<T> findAll();

    List<T> findAllById(@Param("ids") Iterable<Integer> ids);
}
