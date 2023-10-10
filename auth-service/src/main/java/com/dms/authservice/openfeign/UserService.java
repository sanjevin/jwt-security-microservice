package com.dms.authservice.openfeign;

import com.dms.authservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserService {

    @PostMapping("/user/createUsers")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users);

    @GetMapping("/user/getUserByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username);

}
