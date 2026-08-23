package com.example.freya.controllers;

import com.example.freya.entities.User;
import com.example.freya.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Integer userId) {
        return userService.getById(userId);
    }
}
