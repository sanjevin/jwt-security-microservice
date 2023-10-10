package com.dms.authservice.service;

import com.dms.authservice.dto.UserAuthRequestDTO;
import com.dms.authservice.dto.UserAuthResponseDTO;
import com.dms.authservice.dto.UserRegistrationDTO;

public interface AuthenticationService {

    UserAuthResponseDTO register(UserRegistrationDTO userRegistrationDTO);

    UserAuthResponseDTO login(UserAuthRequestDTO userAuthRequestDTO);

    UserAuthResponseDTO unauthorized();

}
