package com.buildcraft.user.controller;

import com.buildcraft.user.entity.User;
import com.buildcraft.user.enums.Role;

import com.buildcraft.user.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/users")

public class UserController {
    private final UserRepository userRepository;

    public UserController( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}/role")
    public Role getRole(@PathVariable Long userId){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent())
        return user.get().getRole();
        else throw new RuntimeException("User not present");
    }
}
