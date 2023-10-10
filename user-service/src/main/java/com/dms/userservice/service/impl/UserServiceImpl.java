package com.dms.userservice.service.impl;

import com.dms.userservice.entity.User;
import com.dms.userservice.repository.UserRepository;
import com.dms.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
                .orElse(null);
    }

    @Override
    public List<User> createUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

}
