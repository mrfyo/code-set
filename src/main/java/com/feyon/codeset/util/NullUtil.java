package com.feyon.codeset.util;

/**
 * @author Feng Yong
 */
public class NullUtil {

    public static <T> T defaultValue(T t, T defaultVal) {
        return t == null ? defaultVal : t;
    }
}
