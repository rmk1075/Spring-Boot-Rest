package com.spring.practice.rest.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.common.exception.base.UnauthorizedException;
import com.spring.practice.rest.model.user.dto.UserCreate;
import com.spring.practice.rest.model.user.dto.UserInfo;
import com.spring.practice.rest.service.user.UserService;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


/**
 * Test code for UserController.
 */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTest {

  @Autowired
  private UserController userController;

  @Autowired
  private UserService userService;

  @Autowired
  private CommonMapper mapper;

  private Long testId;
  private final String testUid = "testId";
  private final String testPwd = "$2y$10$nOB0T9ta16XuUNhOQDw.8.iVKAJOIHQWw5xdvWmbfxbuDEun3vBBK";
  private final String testName = "testName";
  private final String testEmail = "test@email.com";

  @BeforeEach
  void setup() {
    UserInfo testUser = userController.createUser(
      new UserCreate(testUid, testPwd, testName, testEmail));
    testId = testUser.getId();
  }

  @AfterEach
  void tearDown() {
    try {
      userController.deleteUser(testId, mapper.userToUserInfo(userService.getUser(testId)));
    } catch (NoSuchElementException e) {
      e.printStackTrace();
    } catch (UnauthorizedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testCreateUser() {
    // uid duplication check
    UserCreate userCreate = new UserCreate(testUid, testPwd, testName, testEmail);
    assertThrows(
        IllegalArgumentException.class,
        () -> userController.createUser(userCreate)
    );
    
    // email duplication check
    String uid = "testId2";
    userCreate.setUid(uid);
    assertThrows(
        IllegalArgumentException.class,
        () -> userController.createUser(userCreate)
    );

    String email = "test2@email.com";
    userCreate.setEmail(email);
    UserInfo userInfo = userController.createUser(userCreate);
    assertTrue(userInfo.getUid().equals(uid));
    assertTrue(userInfo.getName().equals(testName));
    // assertTrue(userInfo.getEmail().equals(email));
  }

  @Test
  void testDeleteUser() {

  }

  @Test
  void testGetUser() {

  }

  @Test
  void testGetUsers() {

  }

  @Test
  void testUpdateUser() {

  }
}
