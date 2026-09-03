package com.salest.freya.controllers;


import com.salest.freya.dtos.user.CreateUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") Integer userId) {
        return userService.getById(userId);
    }

    @GetMapping
    public List<UserDTO> getAll(){
        return userService.getAll();
    }


    @PostMapping
    public UserDTO create(@RequestBody CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }

    /*
    @PutMapping("/{userId}")
    public UserDTO update(@PathVariable("userId") Integer userId, @RequestBody user userDetails){
        return userService.update(userId, userDetails);
    }
     */
    
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Integer userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
    
}
