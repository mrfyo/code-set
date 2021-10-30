package com.feyon.codeset.mapper;

import com.feyon.codeset.common.Pageable;
import com.feyon.codeset.entity.Question;
import com.feyon.codeset.entity.Solution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class SolutionMapperTest {

    @Autowired
    private SolutionMapper mapper;

    @Test
    void insert() {
        Solution solution = new Solution();
        solution.setTitle("暴力解法--两数之和");
        solution.setQuestionId(1);
        solution.setUserId(1);
        solution.setCreateAt(LocalDateTime.now());
        assertEquals(1, mapper.insert(solution));
    }

    @Test
    void update() {
        Solution solution = mapper.findById(1).orElse(null);
        assertNotNull(solution);
        solution.setQuestionId(2);
        assertEquals(1, mapper.update(solution));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(1));
    }

    @Test
    void findById() {
        Solution solution = mapper.findById(1).orElse(null);
        assertNotNull(solution);
        assertEquals(1, solution.getId());
        assertEquals("暴力解法--两数之和", solution.getTitle());
        assertNotNull(solution.getCreateAt());
        assertEquals(2021, solution.getCreateAt().getYear());
    }

    @Test
    void findAll() {
        List<Solution> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }

    @Test
    void findAllByExample() {
        Solution solution = new Solution();
        solution.setQuestionId(1);
        mapper.findAllByExample(solution);
    }
}