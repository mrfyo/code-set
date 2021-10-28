package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.SubmissionContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class SubmissionContentMapperTest {

    @Autowired
    private SubmissionContentMapper mapper;

    @Test
    void insert() {
        var submission = new SubmissionContent();
        submission.setSubmissionId(1);
        submission.setContent("PASS");
        assertEquals(1, mapper.insert(submission));
    }

    @Test
    void update() {
        var submission = mapper.findById(1);
        assertNotNull(submission);
        submission.setContent("SUCCESS");
        assertEquals(1, mapper.update(submission));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(1));
    }

    @Test
    void findById() {
        var submission = mapper.findById(1);
        System.out.println(submission);
        assertNotNull(submission);
        assertEquals("PASS", submission.getContent());
    }

    @Test
    void findAll() {
        List<SubmissionContent> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}