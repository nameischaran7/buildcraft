package com.buildcraft.user.service;

import com.buildcraft.user.dto.LoginRequest;
import com.buildcraft.user.dto.RegisterRequest;
import com.buildcraft.user.entity.User;
import com.buildcraft.user.enums.Role;
import com.buildcraft.user.repository.UserRepository;
import com.buildcraft.user.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());

        // Important part 🔥
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Default role
        user.setRole(Role.CLIENT);

        return userRepository.save(user);
    }
    public String login(LoginRequest request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (!user.isPresent())throw new RuntimeException("Invalid Email or Password");
        User user1=user.get();
        if(passwordEncoder.matches(request.getPassword(),user1.getPassword())){
            String token = jwtService.generateToken(user1);
            return token;
        }
        else throw new RuntimeException("Invalid Email or Password");
    }
}