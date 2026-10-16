package com.salest.freya.controllers;

import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.dtos.user.UserSignUpDTO;
import com.salest.freya.entities.User;
import com.salest.freya.repositories.UserRepository;
import com.salest.freya.security.JwtUtil;
import com.salest.freya.services.UserService;
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
    UserService userService;
    @Autowired
    JwtUtil jwtUtils;
    @PostMapping("/signin")
    public String authenticateUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		userSignUpDTO.username(),
                		userSignUpDTO.password()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
    @PostMapping("/signup")
    public UserDTO registerUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        return userService.create(userSignUpDTO);
    }
}