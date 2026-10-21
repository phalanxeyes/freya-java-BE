package com.salest.freya.services;

import com.salest.freya.dtos.user.AuthUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.dtos.user.UserSignInDTO;
import com.salest.freya.entities.User;
import com.salest.freya.exceptions.InvalidCredentialsException;
import com.salest.freya.mapper.UserMapper;
import com.salest.freya.repositories.UserRepository;
import com.salest.freya.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    JwtUtil jwtUtils;

    public AuthUserDTO authUser(UserSignInDTO signInDTO){
        User user = userRepo.findByUsername(signInDTO.username());
        if(user == null){
            throw new InvalidCredentialsException("Invalid username or password"); // CWE-204 : https://cwe.mitre.org/data/definitions/204.html
            // NACHO : en el sign up es bastante imposible de atajar, pero acá lo mejor es dar un mensaje lo más ambiguo posible
        }
        boolean isPasswordCorrect = encoder.matches(signInDTO.password(), user.getPassword());
        if(!isPasswordCorrect){
            throw new InvalidCredentialsException("Invalid username or password");
        }
        String accessToken = jwtUtils.generateToken(user.getUsername());
        return new AuthUserDTO(userMapper.userToUserDTO(user), accessToken);
    }
}
