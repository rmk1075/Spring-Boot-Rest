package com.spring.practice.rest.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserServiceImplTest {

  private Long ID;
  private final String UID = "testId";
  private final String NAME = "testName";
  private final String EMAIL = "test@email.com";

  @Autowired private UserService userService;

  @Autowired
  @Qualifier("JpaUserRepository")
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    User user = userRepository.save(new User(UID, NAME, EMAIL, ""));
    ID = user.getId();
    System.out.println(user);
  }

  @AfterEach
  void tearDown() {
    User user = userRepository.findByUid(UID);
    if (user != null) {
      user = userRepository.delete(user);
    }
    System.out.println(user);
  }

  @Test
  void testCreateUser() {
    String uid = "testtest";
    String name = "created";
    String email = "test@gmail.com";
    UserCreate create = new UserCreate(uid, name, email);
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
    List<UserInfo> users = userService.getUsers(0, 100);

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
