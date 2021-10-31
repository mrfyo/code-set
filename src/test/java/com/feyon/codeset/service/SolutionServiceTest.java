package com.feyon.codeset.service;

import com.feyon.codeset.form.SolutionForm;
import com.feyon.codeset.query.SolutionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class SolutionServiceTest {

    @Autowired
    private SolutionService solutionService;

    @Test
    void listAll() {
        var query = new SolutionQuery();
        query.setTags(List.of(13));
        solutionService.listAll(query).forEach(System.out::println);
    }

    @Test
    void save() {
        SolutionForm form = new SolutionForm();
        form.setUserId(1);
        form.setQuestionId(1);
        form.setTitle("暴力解法");
        form.setContent("暴力解法，解题思路如下");
        form.setTagIds(List.of(17));

        solutionService.save(form);
    }

    @Test
    void update() {
        SolutionForm form = new SolutionForm();
        form.setQuestionId(1);
        form.setTitle("画解算法：1. 两数之和");
        form.setContent("画解算法：1. 两数之和 解题思路如下");
        form.setTagIds(List.of(17));
        solutionService.update(2, form);
    }

    @Test
    void delete() {
        solutionService.remove(2);
    }
    
    
}