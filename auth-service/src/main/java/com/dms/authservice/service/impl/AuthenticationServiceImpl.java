package com.dms.authservice.service.impl;

import com.dms.authservice.dto.UserAuthRequestDTO;
import com.dms.authservice.dto.UserAuthResponseDTO;
import com.dms.authservice.dto.UserRegistrationDTO;
import com.dms.authservice.model.User;
import com.dms.authservice.openfeign.UserService;
import com.dms.authservice.service.AuthenticationService;
import com.dms.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserService userService;

    @Override
    public UserAuthResponseDTO register(UserRegistrationDTO userRegistrationDTO) {
        User user = User.builder()
                .username(userRegistrationDTO.getUsername())
                .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .email(userRegistrationDTO.getEmail())
                .build();

        userService.createUsers(List.of(user));

        String jwt = jwtService.generateToken(user.getUsername());

        return UserAuthResponseDTO.builder()
                .username(userRegistrationDTO.getUsername())
                .token(jwt)
                .build();
    }

    @Override
    public UserAuthResponseDTO login(UserAuthRequestDTO userAuthRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthRequestDTO.username(),
                userAuthRequestDTO.password()));

        UserAuthResponseDTO userAuthResponseDTO;

        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(userAuthRequestDTO.username());
            userAuthResponseDTO = UserAuthResponseDTO.builder()
                    .username(userAuthRequestDTO.username())
                    .token(jwt)
                    .build();
        } else {
            userAuthResponseDTO = UserAuthResponseDTO
                    .builder()
                    .username(null)
                    .token(null)
                    .build();
        }

        return userAuthResponseDTO;
    }

    @Override
    public UserAuthResponseDTO unauthorized() {
        return UserAuthResponseDTO.builder().build();
    }

}
