package com.feyon.codeset.service;

import com.feyon.codeset.form.QuestionForm;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.vo.QuestionDetailVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void listAll() {
        QuestionQuery query = new QuestionQuery();
        query.setStatus(1);
        query.setPage(1);
        query.setSize(10);
        System.out.println(questionService.listAll(query));
    }

    @Test
    void findOne() {
        QuestionDetailVO vo = questionService.findOne(75);
        System.out.println(vo);
    }

    @Test
    void save() {
        QuestionForm form = new QuestionForm();
        form.setTitle("盛最多水的容器");
        form.setDifficulty(2);
        form.setTagIdList(Collections.singletonList(6));
        questionService.save(form);
    }

    @Test
    void remove() {
        questionService.remove(75);
    }

    @Test
    void update() {
        QuestionForm form = new QuestionForm();
        form.setTitle("两数之和");
        form.setDifficulty(1);
        form.setTagIdList(Arrays.asList(6, 7));
        questionService.update(75, form);
    }
}