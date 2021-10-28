package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class TagMapperTest {

    @Autowired
    private TagMapper mapper;

    @Test
    void insert() {
        Tag tag = new Tag();
        tag.setTitle("动态规划");
        assertEquals(1, mapper.insert(tag));
    }

    @Test
    void update() {
        var user = mapper.findById(1);
        assertNotNull(user);
        user.setTitle("双向链表");
        assertEquals(1, mapper.update(user));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(1));
    }

    @Test
    void findById() {
        var role = mapper.findById(1);
        assertNotNull(role);
        assertEquals(1, role.getId());
        assertEquals("链表", role.getTitle());
    }

    @Test
    void findAll() {
        List<Tag> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}