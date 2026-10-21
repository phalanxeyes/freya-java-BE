package com.salest.freya.controllers;

import com.salest.freya.dtos.user.AuthUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.dtos.user.UserSignInDTO;
import com.salest.freya.dtos.user.UserSignUpDTO;
import com.salest.freya.entities.User;
import com.salest.freya.repositories.UserRepository;
import com.salest.freya.security.JwtUtil;
import com.salest.freya.services.AuthService;
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
    AuthService authService;

    @PostMapping("/signin")
    public AuthUserDTO authenticateUser(@RequestBody UserSignInDTO signInDTO) {
        return authService.authUser(signInDTO);
    }
    @PostMapping("/signup")
    public AuthUserDTO registerUser(@RequestBody UserSignUpDTO signUpDTO) {
        UserDTO _userDTO = userService.create(signUpDTO);
        return authService.authUser(
                new UserSignInDTO(signUpDTO.username(), signUpDTO.password())
        );
    }
}