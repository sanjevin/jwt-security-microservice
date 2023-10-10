package com.dms.userservice.service;

import com.dms.userservice.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getUsers();

    public User getUserByUsername(String username);

    public List<User> createUsers(List<User> users);

}
