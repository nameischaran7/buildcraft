package com.buildcraft.user.controller;

import com.buildcraft.user.dto.RegisterRequest;
import com.buildcraft.user.entity.User;
import com.buildcraft.user.repository.UserRepository;
import com.buildcraft.user.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }
@PostMapping("/register")
    public String userRegister(@RequestBody RegisterRequest registerRequest){

        authService.register(registerRequest);
        return "User registered successfully";
}

}
