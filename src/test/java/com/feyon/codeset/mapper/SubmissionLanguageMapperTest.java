package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.SubmissionLanguage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class SubmissionLanguageMapperTest {

    @Autowired
    private SubmissionLanguageMapper mapper;

    @Test
    void insert() {
        SubmissionLanguage lang = new SubmissionLanguage();
        lang.setName("Java");
        assertEquals(1, mapper.insert(lang));
    }

    @Test
    void update() {
        var lang = mapper.findById(1).orElse(null);
        assertNotNull(lang);
        lang.setName("C");
        assertEquals(1, mapper.update(lang));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(3));
    }

    @Test
    void findById() {
        var lang = mapper.findById(1).orElse(null);
        assertNotNull(lang);
        assertEquals(1, lang.getId());
        assertEquals("C++", lang.getName());
    }

    @Test
    void findAll() {
        List<SubmissionLanguage> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}