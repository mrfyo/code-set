package com.feyon.codeset.mapper;

import com.feyon.codeset.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class RoleMapperTest {

    @Autowired
    private RoleMapper mapper;

    @Test
    void insert() {
        Role role = new Role();
        role.setName("vip");
        role.setLocalName("Vip用户");
        assertEquals(1, mapper.insert(role));
    }

    @Test
    void update() {
        var role = mapper.findById(1).orElse(null);
        assertNotNull(role);
        assertEquals(1, mapper.update(role));
    }

    @Test
    void deleteById() {
        assertEquals(1, mapper.deleteById(3));
    }

    @Test
    void findById() {
        var role = mapper.findById(1).orElse(null);
        assertNotNull(role);
        assertEquals(1, role.getId());
        assertEquals("admin", role.getName());
        assertEquals("管理员", role.getLocalName());
    }

    @Test
    void findAll() {
        List<Role> list = mapper.findAll();
        assertTrue(list.size() != 0);
        list.forEach(System.out::println);
    }
}