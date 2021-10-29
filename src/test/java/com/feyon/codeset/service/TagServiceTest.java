package com.feyon.codeset.service;

import com.feyon.codeset.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    void save() {
        Tag tag = new Tag();
        tag.setTitle("数学");
        tagService.save(tag);
    }

    @Test
    void remove() {
        tagService.remove(1);
    }

    @Test
    void update() {
        Tag tag = new Tag();
        tag.setTitle("数组2");
        tagService.update(1, tag);
    }

    @Test
    void findAll() {
        tagService.findAll().forEach(System.out::println);
    }
}