package com.feyon.codeset.service;

import com.feyon.codeset.form.QuestionForm;
import com.feyon.codeset.query.QuestionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    void save() {
        QuestionForm form = new QuestionForm();
        form.setNumber(10);
        form.setTitle("正则表达式匹配");
        form.setDifficulty(3);
        form.setTagIdList(List.of(8, 11));
        questionService.save(form);
    }

    @Test
    void remove() {
        questionService.remove(23);
    }

    @Test
    void update() {
        QuestionForm form = new QuestionForm();
        form.setNumber(1);
        form.setTitle("两数之和");
        form.setDifficulty(1);
        form.setTagIdList(List.of(6, 7));
        questionService.update(1, form);
    }
}