package com.feyon.codeset.service;

import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.form.TagForm;
import com.feyon.codeset.vo.TagVO;

import java.util.List;

/**
 * @author Feng Yong
 */
public interface TagService {

    /**
     * save a tag
     * @param form a tag
     */
    void save(TagForm form);

    /**
     * update tag
     * @param tagId tag's id
     * @param form tag object.
     */
    void update(Integer tagId, TagForm form);
    /**
     * remove tag
     * @param tagId tag's id
     */
    void remove(Integer tagId);

    /**
     * return all tag.
     * @return all tag
     */
    List<TagVO> findAll();

    /**
     * return a tag according to id.
     * @param tagId tag id
     * @return a tag
     */
    TagVO findOne(Integer tagId);
}
