package com.spring.practice.rest.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.repository.UserRepository;
import com.spring.practice.rest.service.UserService;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserServiceImplTest {

    private Long ID;
    private final String UID = "testId";
    private final String NAME = "testName";

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("JpaUserRepository")
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        User user = userRepository.save(new User(UID, NAME));
        ID = user.getId();
        System.out.println(user);
    }

    @AfterEach
    void tearDown() {
        User user = userRepository.findByUid(UID);
        if(user != null) user = userRepository.delete(user);
        System.out.println(user);
    }

    @Test
    void testCreateUser() {
        String uid = "testtest";
        String name = "created";
        UserCreate create = UserCreate.builder().uid(uid).name(name).build();
        UserInfo user = userService.createUser(create);

        assertTrue(user.getUid().equals(create.getUid()));
        assertTrue(user.getName().equals(create.getName()));
    }

    @Test
    void testDeleteUser() {
        UserInfo user = userService.deleteUser(ID);

        assertTrue(user.getUid().equals(UID));
        assertTrue(user.getName().equals(NAME));

        assertNull(userRepository.findByUid(UID));
    }

    @Test
    void testGetUser() {
        UserInfo user = userService.getUser(ID);

        assertTrue(user.getUid().equals(UID));
        assertTrue(user.getName().equals(NAME));
    }

    @Test
    void testGetUsers() {
        List<UserInfo> users = userService.getUsers();

        assertTrue(0 < users.size());
        System.out.println();
    }

    @Test
    void testUpdateUser() {
        UserUpdate update = new UserUpdate();
        update.setName("updated");

        UserInfo user = userService.updateUser(ID, update);

        assertNotNull(user);
        assertTrue(user.getUid().equals(UID));
        assertFalse(user.getName().equals(NAME));
        assertTrue(user.getName().equals(update.getName()));
    }
}
