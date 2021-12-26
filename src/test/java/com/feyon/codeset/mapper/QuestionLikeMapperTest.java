package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.QuestionLike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class QuestionLikeMapperTest {

    @Autowired
    private QuestionLikeMapper mapper;

    @Test
    void insert() {
        QuestionLike like = new QuestionLike();
        like.setUserId(1);
        like.setQuestionId(1);
        assertEquals(1, mapper.insert(like));
    }

    @Test
    void update() {
        QuestionLike entity = mapper.findById(75).orElse(null);
        assertNotNull(entity);
        entity.setQuestionId(2);
        assertEquals(1, mapper.update(entity));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(75));
    }

    @Test
    void findById() {
        QuestionLike entity = mapper.findById(1).orElse(null);
        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals(1, entity.getUserId());
        assertEquals(1, entity.getQuestionId());
    }

    @Test
    void findAll() {
        List<QuestionLike> list = mapper.findAll();
        assertNotNull(list);
        list.forEach(System.out::println);
    }
}