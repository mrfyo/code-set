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
        query.setDifficulty(2);
        query.setStatus(0);
        query.setPage(1);
        query.setSize(10);
        System.out.println(questionService.listAll(query));;
    }

    @Test
    void save() {
        QuestionForm form = new QuestionForm();
        form.setTitle("盛最多水的容器");
        form.setDifficulty(2);
        form.setTagIdList(List.of(6));
        questionService.save(form);
    }

    @Test
    void remove() {
        questionService.remove(23);
    }

    @Test
    void update() {
        QuestionForm form = new QuestionForm();
        form.setTitle("两数之和");
        form.setDifficulty(1);
        form.setTagIdList(List.of(6, 7));
        questionService.update(1, form);
    }
}