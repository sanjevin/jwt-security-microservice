package com.dms.authservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

}
