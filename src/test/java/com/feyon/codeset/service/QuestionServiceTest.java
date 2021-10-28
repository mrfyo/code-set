package com.feyon.codeset.service;

import com.feyon.codeset.query.QuestionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void listAll() {
        var query = new QuestionQuery();
        query.setDifficulty(1);
        query.setStatus(2);
        query.setPage(1);
        query.setSize(10);
        System.out.println(questionService.listAll(query));;
    }
}