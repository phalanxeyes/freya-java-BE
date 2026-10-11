package com.salest.freya.controllers;

import com.salest.freya.dtos.user.UserSignUpDTO;
import com.salest.freya.entities.User;
import com.salest.freya.repositories.UserRepository;
import com.salest.freya.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @PostMapping("/signin")
    public String authenticateUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		userSignUpDTO.userName(),
                		userSignUpDTO.password()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
    @PostMapping("/signup")
    public String registerUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        if (userRepository.existsByUsername(userSignUpDTO.userName())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        User newUser = new User(
                null,
                userSignUpDTO.userName(),
                encoder.encode(userSignUpDTO.password())
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}