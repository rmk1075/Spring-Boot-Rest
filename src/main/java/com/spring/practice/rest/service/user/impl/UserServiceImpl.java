package com.spring.practice.rest.service.user.impl;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserPatch;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
  // @Qualifier("JdbcTemplateUserRepository") // 중복된 타입의 bean 이 존재하는 경우 @Qualifier 어노테이션을 통해서 사용할
  // bean name 을 지정할 수 있다.
  // @Qualifier("MockUserRepository")
  // @Qualifier("JdbcUserRepository")
  @Qualifier("JpaUserRepository")
  private UserRepository userRepository;

  @Autowired private CommonMapper mapper;

  @Override
  public User getUser(Long id) throws NoSuchElementException {
    User user = userRepository.findById(id);
    if (user == null) {
      throw new NoSuchElementException(String.format("User[id=%s] is not exists.", id));
    }
    return user;
  }

  @Override
  public List<User> getUsers(int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    List<User> users = userRepository
            .findAll(pageable)
            .getContent();
    return users;
  }

  @Override
  public User createUser(UserCreate userCreate) {
    User user = userRepository.findByUid(userCreate.getUid());
    if (user != null) {
      throw new IllegalArgumentException(
          String.format("User[uid=%s] is already exists.", user.getUid()));
    }

    user = userRepository.findByEmail(userCreate.getEmail());
    if (user != null) {
      throw new IllegalArgumentException(
          String.format("User[email=%s] is already exists.", user.getEmail()));
    }

    UserInfo userInfo = UserInfo.builder()
        .uid(userCreate.getUid())
        .name(userCreate.getName())
        .email(userCreate.getEmail())
        .desc(userCreate.getDesc())
        .build();
    User created = userRepository.save(mapper.userInfoToUser(userInfo));
    return created;
  }

  @Override
  public User patchUser(Long id, UserPatch userPatch) {
    User user = this.getUser(id);

    // name validation
    String name = userPatch.getName();
    if (name != null) {
      if (userRepository.findByName(name) != null) {
        throw new IllegalArgumentException(
          String.format("User[name=%s] is already exists", name)
        );
      }
      user.setName(name);
    }

    // email validation
    String email = userPatch.getEmail();
    if (email != null) {
      if (userRepository.findByEmail(email) != null) {
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

    User updated = userRepository.update(user);
    return updated;
  }

  @Override
  public User updateUser(Long id, UserUpdate userUpdate) {
    User user = this.getUser(id);

    // name validation
    String name = userUpdate.getName();
    if (name == null) {
      throw new IllegalArgumentException(
        String.format("User update name is null.")
      );
    }
    if (userRepository.findByName(name) != null) {
      throw new IllegalArgumentException(
        String.format("User[name=%s] is already exists", name)
      );
    }
    user.setName(name);

    // email validation
    String email = userUpdate.getEmail();
    if (email == null) {
      throw new IllegalArgumentException(
        String.format("User update email is null.")
      );
    }
    if (userRepository.findByEmail(email) != null) {
      throw new IllegalArgumentException(
        String.format("User[email=%s] is already exists.", email));
    }
    user.setEmail(email);

    // desc validation
    String desc = userUpdate.getDesc();
    if (desc == null) {
      throw new IllegalArgumentException(
        String.format("User update desc is null.")
      );
    }
    user.setDesc(desc);

    User updated = userRepository.update(user);
    return updated;
  }

  @Override
  public User deleteUser(Long id) {
    User user = this.getUser(id);
    User deleted = userRepository.delete(user);
    return deleted;
  }
}
