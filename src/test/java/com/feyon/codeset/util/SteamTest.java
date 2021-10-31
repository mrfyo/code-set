package com.feyon.codeset.util;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * @author Feng Yong
 */
public class SteamTest {

    @Test
    void test1() {
        Incrementer incrementer = new Incrementer();
        Stream.of(1, 2, 3, 4, 6, 7, 8)
                .filter(n -> n > 3)
                .peek(incrementer::increment)
                .skip(1)
                .limit(3)
                .forEachOrdered(System.out::println);

        System.out.println(incrementer.get());
    }
}
