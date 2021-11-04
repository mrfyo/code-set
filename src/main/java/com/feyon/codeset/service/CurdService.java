package com.feyon.codeset.service;

import com.feyon.codeset.exception.EntityException;

/**
 * @author Feng Yong
 */
public interface CurdService<T> {

    /**
     * save an entity
     * @param entity entity object.
     */
    void save(T entity);

    /**
     * remove an entity
     * @param id entity id.
     * @throws EntityException if entity is not exist, will throw this.
     */
    void removeById(Integer id);

    /**
     * update an entity
     * @param entity entity object.
     * @throws EntityException if entity is not exist, will throw this.
     */
    void updateById(T entity);

    /**
     * return an entity
     * @param id entity id.
     * @return an entity.
     * @throws EntityException if entity is not exist, will throw this.
     */
    T getById(Integer id);

}
