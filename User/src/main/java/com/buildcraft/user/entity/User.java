package com.buildcraft.user.entity;

import com.buildcraft.user.enums.Role;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}