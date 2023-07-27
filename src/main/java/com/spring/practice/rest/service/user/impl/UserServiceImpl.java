package com.spring.practice.rest.service.user.impl;

import com.spring.practice.rest.common.constant.Role;
import com.spring.practice.rest.common.exception.user.UserInfoDuplicatedException;
import com.spring.practice.rest.common.exception.user.UserNotFoundException;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserCreate;
import com.spring.practice.rest.model.user.dto.UserPatch;
import com.spring.practice.rest.model.user.dto.UserUpdate;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** UserService implements class. */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public User getUser(Long id) throws NoSuchElementException {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException(id);
    }
    return user.get();
  }

  @Override
  public User getUserByUid(String uid) throws NoSuchElementException {
    Optional<User> user = userRepository.findByUid(uid);
    if (user.isEmpty()) {
      throw new UserNotFoundException(uid);
    }
    return user.get();
  }

  @Override
  public List<User> getUsers(int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    return userRepository.findAll(pageable).getContent();
  }

  @Override
  public User createUser(UserCreate userCreate) throws UserInfoDuplicatedException {
    this.validateUserCreate(userCreate);
    User user =
        new User(
            userCreate.getUid(),
            userCreate.getPassword(),
            Role.USER.name(),
            userCreate.getName(),
            userCreate.getEmail(),
            userCreate.getDesc());
    return userRepository.save(user);
  }

  private void validateUserCreate(UserCreate userCreate) {
    this.validateUserIdDuplications(userCreate.getUid());
    this.validateUserEmailDuplications(userCreate.getEmail());
  }

  private void validateUserIdDuplications(String uid) {
    if (userRepository.findByUid(uid).isPresent()) {
      throw new UserInfoDuplicatedException(uid);
    }
  }

  private void validateUserEmailDuplications(String email) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new UserInfoDuplicatedException(email);
    }
  }

  @Override
  public User patchUser(Long id, UserPatch userPatch) throws UserInfoDuplicatedException {
    User user = this.getUser(id);
    user = this.updateUserByUserPatch(user, userPatch);
    return userRepository.save(user);
  }

  private User updateUserByUserPatch(User user, UserPatch userPatch)
      throws UserInfoDuplicatedException {
    String name = userPatch.getName();
    if (name != null) {
      this.validateUserName(name);
      user.setName(name);
    }

    String email = userPatch.getEmail();
    if (email != null) {
      this.validateUserEmailDuplications(email);
      user.setEmail(email);
    }

    String desc = userPatch.getDesc();
    if (desc != null) {
      user.setDesc(desc);
    }

    return user;
  }

  private void validateUserName(String name) throws UserInfoDuplicatedException {
    if (userRepository.findByName(name).isPresent()) {
      throw new UserInfoDuplicatedException(name);
    }
  }

  @Override
  public User updateUser(Long id, UserUpdate userUpdate) {
    User user = this.getUser(id);

    // name validation
    String name = userUpdate.getName();
    if (userRepository.findByName(name).isPresent()) {
      throw new UserInfoDuplicatedException(name);
    }
    user.setName(name);

    // email validation
    String email = userUpdate.getEmail();
    if (userRepository.findByEmail(email).isPresent()) {
      throw new UserInfoDuplicatedException(email);
    }
    user.setEmail(email);

    // desc validation
    String desc = userUpdate.getDesc();
    user.setDesc(desc);

    return userRepository.save(user);
  }

  @Override
  public User deleteUser(Long id) {
    User user = this.getUser(id);
    userRepository.delete(user);
    return user;
  }
}
