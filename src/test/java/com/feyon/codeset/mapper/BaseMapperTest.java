package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class BaseMapperTest {

    @Autowired
    private QuestionMapper mapper;

    @Test
    void insert() {
        Question question = new Question();
        question.setNumber(8);
        question.setTitle("符串转换整数");
        question.setLevel(2);
        assertEquals(1, mapper.insert(question));
        assertEquals(13, question.getId());
    }

    @Test
    void update() {
        Question question = mapper.findById(1);
        assertNotNull(question);
        question.setLevel(3);
        assertEquals(1, mapper.update(question));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(7));
    }

    @Test
    void findById() {
        Question question = mapper.findById(1);
        assertNotNull(question);
        assertEquals(1, question.getId());
        assertEquals("两数之和", question.getTitle());
        assertEquals(1, question.getLevel());
    }

    @Test
    void findAll() {
        List<Question> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}