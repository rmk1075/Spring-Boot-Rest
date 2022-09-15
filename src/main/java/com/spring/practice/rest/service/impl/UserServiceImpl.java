package com.spring.practice.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;
import com.spring.practice.rest.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    // @Qualifier("JdbcTemplateUserRepository") // 중복된 타입의 bean 이 존재하는 경우 @Qualifier 어노테이션을 통해서 사용할 bean name 을 지정할 수 있다.
    // @Qualifier("MockUserRepository")
    @Qualifier("JdbcUserRepository")
    private UserRepository userRepository;

    @Override
    public User getUser(String id) {
        User user = userRepository.findById(id);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = this.userRepository.findAllUsers();
        return users;
    }

    @Override
    public void addUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    @Override
    public void removeUser(String id) {
        userRepository.removeUser(id);
    }
    
}