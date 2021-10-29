package com.feyon.codeset.service;

import com.feyon.codeset.vo.TagVO;

import java.util.List;

/**
 * @author Feng Yong
 */
public interface TagService {



    /**
     * return all tag.
     * @return all tag
     */
    List<TagVO> findAll();
}
