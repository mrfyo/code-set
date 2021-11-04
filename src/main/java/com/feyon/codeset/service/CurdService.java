package com.feyon.codeset.service;

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
     */
    void removeById(Integer id);

    /**
     * update an entity
     * @param id entity id.
     * @param entity entity object.
     */
    void updateById(Integer id, T entity);

    /**
     * return an entity
     * @param id entity id.
     * @return an entity.
     */
    T getById(Integer id);

}
