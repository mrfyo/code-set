package com.feyon.codeset.util;

import com.feyon.codeset.common.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Feng Yong
 */
public class PageUtils {

    public static  <T> List<T> selectPage(List<T> source, Pageable pageable) {
        List<T> dist = new ArrayList<>(pageable.getOffset());
        int start = Math.max(0, pageable.getOffset());
        int end = Math.min(source.size(), pageable.getOffset() + pageable.getLimit());
        for(int i = start; i < end; i++) {
            dist.add(source.get(i));
        }
        return dist;
    }
}
