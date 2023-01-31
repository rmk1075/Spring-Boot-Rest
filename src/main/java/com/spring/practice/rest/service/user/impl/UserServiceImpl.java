
package com.spring.practice.rest.service.user.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.service.user.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    // @Qualifier("JdbcTemplateUserRepository") // 중복된 타입의 bean 이 존재하는 경우 @Qualifier 어노테이션을 통해서 사용할 bean name 을 지정할 수 있다.
    // @Qualifier("MockUserRepository")
    // @Qualifier("JdbcUserRepository")
    @Qualifier("JpaUserRepository")
    private UserRepository userRepository;

    @Autowired
    private CommonMapper mapper;

    @Override
    public UserInfo getUser(Long id) {
        User user = userRepository.findById(id);
        if(user == null) throw new NoSuchElementException(String.format("User[id=%s] is not exists.", id));
        return mapper.userToUserInfo(user);
    }

    @Override
    public List<UserInfo> getUsers() {
        List<UserInfo> users = this.userRepository.findAll().stream().map(user -> mapper.userToUserInfo(user)).collect(Collectors.toList());
        return users;
    }

    @Override
    public UserInfo createUser(UserCreate userCreate) {
        User user = userRepository.findByUid(userCreate.getUid());
        if(user != null) throw new IllegalArgumentException(String.format("User[uid=%s] is already exists.", user.getUid()));

        UserInfo userInfo = UserInfo.builder().uid(userCreate.getUid()).name(userCreate.getName()).build();
        User created = userRepository.save(mapper.userInfoToUser(userInfo));
        return mapper.userToUserInfo(created);
    }

    @Override
    public UserInfo updateUser(Long id, UserUpdate userUpdate) {
        UserInfo user = this.getUser(id);
        user.setName(userUpdate.getName());
        User updated = userRepository.update(mapper.userInfoToUser(user));
        return mapper.userToUserInfo(updated);
    }

    @Override
    public UserInfo deleteUser(Long id) {
        UserInfo user = this.getUser(id);
        User deleted = userRepository.delete(mapper.userInfoToUser(user));
        return mapper.userToUserInfo(deleted);
    }
    
}
