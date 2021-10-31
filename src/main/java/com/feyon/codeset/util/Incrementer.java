package com.feyon.codeset.util;

/**
 * @author Feng Yong
 */
public class Incrementer {
    private long count;

    public <T> void increment(T element) {
        count++;
    }

    public long get() {
        return count;
    }
}
