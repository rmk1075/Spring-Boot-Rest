package com.spring.practice.rest.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.practice.rest.common.CommonMapper;
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

/**
 * Test code for UserServiecImpl.
 */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserServiceImplTest {

  private Long testId;
  private final String testUid = "testId";
  private final String testName = "testName";
  private final String testEmail = "test@email.com";

  @Autowired private UserService userService;

  @Autowired private CommonMapper mapper;

  @Autowired
  @Qualifier("JpaUserRepository")
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    User user = userRepository.save(new User(testUid, testName, testEmail, ""));
    testId = user.getId();
    System.out.println(user);
  }

  @AfterEach
  void tearDown() {
    User user = userRepository.findByUid(testUid);
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
    User user = userService.createUser(create);

    assertTrue(user.getUid().equals(create.getUid()));
    assertTrue(user.getName().equals(create.getName()));
  }

  @Test
  void testDeleteUser() {
    UserInfo user = userService.deleteUser(testId);

    assertTrue(user.getUid().equals(testUid));
    assertTrue(user.getName().equals(testName));

    assertNull(userRepository.findByUid(testUid));
  }

  @Test
  void testGetUser() {
    User user = userService.getUser(testId);

    assertTrue(user.getUid().equals(testUid));
    assertTrue(user.getName().equals(testName));
  }

  @Test
  void testGetUsers() {
    List<UserInfo> users = userService
        .getUsers(0, 100)
        .stream()
        .map(user -> mapper.userToUserInfo(user))
        .toList();

    assertTrue(0 < users.size());
    System.out.println();
  }

  @Test
  void testUpdateUser() {
    UserUpdate update = new UserUpdate();
    update.setName("updated");

    UserInfo user = mapper.userToUserInfo(userService.updateUser(testId, update));

    assertNotNull(user);
    assertTrue(user.getUid().equals(testUid));
    assertFalse(user.getName().equals(testName));
    assertTrue(user.getName().equals(update.getName()));
  }
}
