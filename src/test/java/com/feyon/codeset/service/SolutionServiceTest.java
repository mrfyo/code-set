package com.feyon.codeset.service;

import com.feyon.codeset.query.SolutionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SolutionServiceTest {

    @Autowired
    private SolutionService solutionService;

    @Test
    void listAll() {
        var query = new SolutionQuery();
        query.setTags(List.of(13));
        solutionService.listAll(query).forEach(System.out::println);
    }
}