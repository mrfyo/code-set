package com.feyon.codeset.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author Feng Yong
 */
public class SteamTest {

    @Test
    void test1() {
        AtomicInteger counter = new AtomicInteger();
        Stream.of(1, 2, 3, 4, 6, 7, 8)
                .filter(n -> n > 3)
                .peek(i -> {
                    counter.incrementAndGet();
                })
                .skip(2)
                .limit(2)
                .forEachOrdered(System.out::println);

        System.out.println(counter.get());
    }
}
