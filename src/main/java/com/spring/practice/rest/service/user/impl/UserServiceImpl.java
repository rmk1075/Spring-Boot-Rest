package com.spring.practice.rest.service.user.impl;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.common.constants.Role;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserCreate;
import com.spring.practice.rest.model.user.dto.UserDb;
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

/**
 * UserService implements class.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired private CommonMapper mapper;

  @Override
  public User getUser(Long id) throws NoSuchElementException {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NoSuchElementException(String.format("User[id=%d] is not exists.", id));
    }
    return user.get();
  }

  @Override
  public User getUserByUid(String uid) {
    Optional<User> user = userRepository.findByUid(uid);
    if (user.isEmpty()) {
      throw new NoSuchElementException(String.format("User[uid=%s] is not exists.", uid));
    }
    return user.get();
  }

  @Override
  public List<User> getUsers(int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    return userRepository
            .findAll(pageable)
            .getContent();
  }

  @Override
  public User createUser(UserCreate userCreate) {
    if (userRepository.findByUid(userCreate.getUid()).isPresent()) {
      throw new IllegalArgumentException(
          String.format("User[uid=%s] is already exists.", userCreate.getUid()));
    }

    if (userRepository.findByEmail(userCreate.getEmail()).isPresent()) {
      throw new IllegalArgumentException(
          String.format("User[email=%s] is already exists.", userCreate.getEmail()));
    }

    UserDb userDb = UserDb.builder()
        .uid(userCreate.getUid())
        .password(userCreate.getPassword())
        .role(Role.USER.name())
        .name(userCreate.getName())
        .email(userCreate.getEmail())
        .desc(userCreate.getDesc())
        .build();
    return userRepository.save(mapper.userDbToUser(userDb));
  }

  @Override
  public User patchUser(Long id, UserPatch userPatch) {
    User user = this.getUser(id);

    // name validation
    String name = userPatch.getName();
    if (name != null) {
      if (userRepository.findByName(name).isPresent()) {
        throw new IllegalArgumentException(
          String.format("User[name=%s] is already exists", name)
        );
      }
      user.setName(name);
    }

    // email validation
    String email = userPatch.getEmail();
    if (email != null) {
      if (userRepository.findByEmail(email).isPresent()) {
        throw new IllegalArgumentException(
          String.format("User[email=%s] is already exists.", email));
      }
      user.setEmail(email);
    }

    // desc validation
    String desc = userPatch.getDesc();
    if (desc != null) {
      user.setDesc(desc);
    }

    return userRepository.save(user);
  }

  @Override
  public User updateUser(Long id, UserUpdate userUpdate) {
    User user = this.getUser(id);

    // name validation
    String name = userUpdate.getName();
    if (userRepository.findByName(name).isPresent()) {
      throw new IllegalArgumentException(
        String.format("User[name=%s] is already exists", name)
      );
    }
    user.setName(name);

    // email validation
    String email = userUpdate.getEmail();
    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException(
        String.format("User[email=%s] is already exists.", email));
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
