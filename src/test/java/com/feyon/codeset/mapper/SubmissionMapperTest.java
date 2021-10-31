package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Submission;
import com.feyon.codeset.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class SubmissionMapperTest {

    @Autowired
    private SubmissionMapper mapper;

    @Test
    void insert() {
        var submission = new Submission();
        submission.setResult(1);
        submission.setQuestionId(1);
        submission.setUserId(1);
        submission.setTimeCost(30);
        submission.setMemoryCost(5000);
        submission.setLanguageId(2);
        submission.setCreateAt(LocalDateTime.now());
        assertEquals(1, mapper.insert(submission));
    }

    @Test
    void update() {
        var submission = mapper.findById(1).orElse(null);
        assertNotNull(submission);
        submission.setResult(1);
        submission.setQuestionId(2);
        submission.setCreateAt(LocalDateTime.now());
        submission.setLanguageId(1);
        submission.setTimeCost(10);
        submission.setMemoryCost(3000);
        assertEquals(1, mapper.update(submission));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(1));
    }

    @Test
    void findById() {
        var submission = mapper.findById(1).orElse(null);
        System.out.println(submission);
        assertNotNull(submission);
        assertEquals(1, submission.getResult());
        assertEquals(1, submission.getQuestionId());
        assertNotNull(submission.getCreateAt());
        assertEquals(2021, submission.getCreateAt().getYear());
    }

    @Test
    void findAll() {
        List<Submission> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}