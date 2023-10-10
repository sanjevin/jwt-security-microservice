package com.dms.authservice.controller;

import com.dms.authservice.dto.UserAuthRequestDTO;
import com.dms.authservice.dto.UserAuthResponseDTO;
import com.dms.authservice.dto.UserRegistrationDTO;
import com.dms.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserAuthResponseDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            return ResponseEntity.ok(authenticationService.register(userRegistrationDTO));
        } catch (Exception e) {
            log.error("Error at register: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(authenticationService.unauthorized());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDTO> login(@RequestBody UserAuthRequestDTO userAuthRequestDTO) {
        try {
            return ResponseEntity.ok(authenticationService.login(userAuthRequestDTO));
        } catch (Exception e) {
            log.error("Error at login: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(authenticationService.unauthorized());
        }
    }

}
