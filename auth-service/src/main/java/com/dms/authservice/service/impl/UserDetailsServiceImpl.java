package com.dms.authservice.service.impl;

import com.dms.authservice.model.User;
import com.dms.authservice.model.UserDetailsModel;
import com.dms.authservice.openfeign.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username).getBody();

        return Optional.of(user)
                .map(u -> UserDetailsModel.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }

}
