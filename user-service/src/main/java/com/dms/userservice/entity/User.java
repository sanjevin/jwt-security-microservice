package com.dms.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dms_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

}
