package com.feyon.codeset.mapper;

import com.feyon.codeset.common.Pageable;
import com.feyon.codeset.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class QuestionMapperTest {

    @Autowired
    private QuestionMapper mapper;

    @Test
    void insert() {
        Question question = new Question();
        question.setNumber(8);
        question.setTitle("符串转换整数");
        question.setDifficulty(2);
        assertEquals(1, mapper.insert(question));
    }

    @Test
    void update() {
        Question question = mapper.findById(1).orElse(null);
        assertNotNull(question);
        question.setDifficulty(75);
        assertEquals(1, mapper.update(question));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(75));
    }

    @Test
    void findById() {
        Question question = mapper.findById(75).orElse(null);
        assertNotNull(question);
        assertEquals(1, question.getId());
        assertEquals("两数之和", question.getTitle());
        assertEquals(1, question.getDifficulty());
    }

    @Test
    void findAll() {
        List<Question> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}