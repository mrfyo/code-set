package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void insert() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("secret");
        assertEquals(1, mapper.insert(user));
    }

    @Test
    void update() {
        User user = mapper.findById(1).orElse(null);
        assertNotNull(user);
        user.setPassword("admin");
        assertEquals(1, mapper.update(user));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(1));
    }

    @Test
    void findById() {
        User role = mapper.findById(1).orElse(null);
        assertNotNull(role);
        assertEquals(1, role.getId());
        assertEquals("user", role.getUsername());
        assertEquals("user", role.getPassword());
    }

    @Test
    void findAll() {
        List<User> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}