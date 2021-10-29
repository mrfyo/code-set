package com.feyon.codeset.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    void findAll() {
        tagService.findAll().forEach(System.out::println);
    }
}