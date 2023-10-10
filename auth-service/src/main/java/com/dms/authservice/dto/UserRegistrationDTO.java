package com.dms.authservice.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

}
