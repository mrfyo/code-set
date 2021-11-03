package com.feyon.codeset.service;

import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.form.TagForm;
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
        TagForm tag = new TagForm();
        tag.setName("数学");
        tagService.save(tag);
    }

    @Test
    void remove() {
        tagService.remove(1);
    }

    @Test
    void update() {
        TagForm tag = new TagForm();
        tag.setName("数组2");
        tagService.update(1, tag);
    }

    @Test
    void findAll() {
        tagService.findAll().forEach(System.out::println);
    }
}