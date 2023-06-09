package com.spring.practice.rest.service.user.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserCreate;
import com.spring.practice.rest.model.user.dto.UserInfo;
import com.spring.practice.rest.model.user.dto.UserPatch;
import com.spring.practice.rest.model.user.dto.UserUpdate;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final String testPwd = "$2y$10$nOB0T9ta16XuUNhOQDw.8.iVKAJOIHQWw5xdvWmbfxbuDEun3vBBK";
  private final String testRole = "USER";
  private final String testName = "testName";
  private final String testEmail = "test@email.com";

  @Autowired private UserService userService;

  @Autowired private CommonMapper mapper;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    User user = userRepository.save(new User(testUid, testPwd, testRole, testName, testEmail, ""));
    testId = user.getId();
    System.out.println(user);
  }

  @AfterEach
  void tearDown() {
    Optional<User> user = userRepository.findByUid(testUid);
    if (user.isPresent()) {
      userRepository.delete(user.get());
      System.out.println(user.get());
    }
  }

  @Test
  void testCreateUser() {
    String uid = "testtest";
    String password = "$2y$10$nOB0T9ta16XuUNhOQDw.8.iVKAJOIHQWw5xdvWmbfxbuDEun3vBBK";
    String name = "created";
    String email = "test@gmail.com";
    UserCreate create = new UserCreate(uid, password, name, email);
    User user = userService.createUser(create);

    assertEquals(user.getUid(), create.getUid());
    assertEquals(user.getName(), create.getName());
  }

  @Test
  void testDeleteUser() {
    User user = userService.deleteUser(testId);

    assertEquals(testUid, user.getUid());
    assertEquals(testName, user.getName());
    assertEquals(true, userRepository.findByUid(testUid).isEmpty());
  }

  @Test
  void testGetUser() {
    User user = userService.getUser(testId);

    assertEquals(user.getUid(), testUid);
    assertEquals(user.getName(), testName);
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
  void testPatchUser() {
    UserPatch patch = new UserPatch();
    patch.setName("updated");

    UserInfo user = mapper.userToUserInfo(userService.patchUser(testId, patch));

    assertNotNull(user);
    assertEquals(user.getUid(), testUid);
    assertNotEquals(user.getName(), testName);
    assertEquals(user.getName(), patch.getName());
  }

  @Test
  void testUpdateUser() {
    UserUpdate update = new UserUpdate();
    update.setName("updated");

    assertThrows(
      NullPointerException.class,
      () -> userService.updateUser(testId, update)
    );

    update.setEmail("update@email.com");
    update.setDesc("updated desc");
    UserInfo user = mapper.userToUserInfo(userService.updateUser(testId, update));

    assertNotNull(user);
    assertEquals(user.getUid(), testUid);
    assertNotEquals(user.getName(), testName);
    assertEquals(user.getName(), update.getName());
    assertEquals(user.getEmail(), update.getEmail());
    assertEquals(user.getDesc(), update.getDesc());
  }
}
