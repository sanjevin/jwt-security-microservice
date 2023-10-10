package com.dms.userservice.controller;

import com.dms.userservice.entity.User;
import com.dms.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/createUsers")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        return ResponseEntity.ok(userService.createUsers(users));
    }

}
