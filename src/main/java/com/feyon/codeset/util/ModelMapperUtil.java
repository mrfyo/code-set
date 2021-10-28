package com.feyon.codeset.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Yong
 */
public class ModelMapperUtil {

    public static ModelMapper modelMapper = new ModelMapper();

    public static ModelMapper strictModelMapper = new ModelMapper();

    static  {
        strictModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        strictModelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    public static void setModelMapper(ModelMapper modelMapper) {
        ModelMapperUtil.modelMapper = modelMapper;
    }

    public static <T> T map(Object source, Class<T> dist) {
        return modelMapper.map(source, dist);
    }

    public static <T> T map(Object source, Class<T> dist, boolean strict) {
        if(strict) {
            return strictModelMapper.map(source, dist);
        }
        return modelMapper.map(source, dist);
    }


    public static void map(Object source, Object dist) {
        modelMapper.map(source, dist);
    }

    public static void map(Object source, Object dist, boolean strict) {
        if(strict) {
            strictModelMapper.map(source, dist);
            return;
        }
        modelMapper.map(source, dist);
    }

    public static <T> List<T> mapList(List<?> sources, Class<T> dist) {
        List<T> list = new ArrayList<>(sources.size());
        for (Object source : sources) {
            list.add(map(source, dist));
        }
        return list;
    }

    public static <T> List<T> mapList(List<?> sources, Class<T> dist, boolean strict) {
        List<T> list = new ArrayList<>(sources.size());
        for (Object source : sources) {
            list.add(map(source, dist, strict));
        }
        return list;
    }
}
