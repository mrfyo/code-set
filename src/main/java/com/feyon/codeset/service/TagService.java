package com.feyon.codeset.service;

import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.vo.TagVO;

import java.util.List;

/**
 * @author Feng Yong
 */
public interface TagService {

    /**
     * save a tag
     * @param tag a tag
     */
    void save(Tag tag);

    void update(Integer tagId, Tag tag);

    void remove(Integer tagId);

    /**
     * return all tag.
     * @return all tag
     */
    List<TagVO> findAll();
}
