package com.spring.practice.rest.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.repository.UserRepository;
import com.spring.practice.rest.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    // @Qualifier("JdbcTemplateUserRepository") // 중복된 타입의 bean 이 존재하는 경우 @Qualifier 어노테이션을 통해서 사용할 bean name 을 지정할 수 있다.
    // @Qualifier("MockUserRepository")
    // @Qualifier("JdbcUserRepository")
    @Qualifier("JpaUserRepository")
    private UserRepository userRepository;

    @Override
    public UserInfo getUser(String uid) {
        User result = userRepository.findByUid(uid);
        if(result == null) throw new NoSuchElementException(String.format("User[uid=%s] is not exists. uid=%s", uid));
        return result != null ? UserInfo.ofUser(result) : null;
    }

    @Override
    public List<UserInfo> getUsers() {
        List<UserInfo> result = this.userRepository.findAll().stream().map(user -> UserInfo.ofUser(user)).collect(Collectors.toList());
        return result;
    }

    @Override
    public UserInfo createUser(UserCreate userCreate) {
        UserInfo user = this.getUser(userCreate.getUid());
        if(user != null) throw new IllegalArgumentException(String.format("User[uid=%s] is already exists.", user.getUid()));

        user = new UserInfo();
        user.setUid(userCreate.getUid());
        user.setName(userCreate.getName());
        User result = userRepository.save(User.ofUserInfo(user));
        return UserInfo.ofUser(result);
    }

    @Override
    public UserInfo updateUser(String uid, UserUpdate userUpdate) {
        UserInfo user = this.getUser(uid);
        user.setName(userUpdate.getName());
        User result = userRepository.update(User.ofUserInfo(user));
        return UserInfo.ofUser(result);
    }

    @Override
    public UserInfo deleteUser(String uid) {
        UserInfo user = this.getUser(uid);
        User result = userRepository.delete(User.ofUserInfo(user));
        return UserInfo.ofUser(result);
    }
    
}
