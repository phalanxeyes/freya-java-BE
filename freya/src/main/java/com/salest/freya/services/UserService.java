package com.salest.freya.services;

import java.time.LocalDateTime;
import java.util.List;

import com.salest.freya.dtos.user.CreateUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.dtos.user.UserSignUpDTO;
import com.salest.freya.exceptions.BusinessRuleException;
import com.salest.freya.exceptions.EntityAlreadyExistsException;
import com.salest.freya.exceptions.IDNotFoundException;
import com.salest.freya.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salest.freya.entities.User;
import com.salest.freya.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder encoder;
	
	public UserDTO getById(Integer userId) {
        
        if (userId == null) {
            throw new IllegalArgumentException("Invalid cover ID");
        }
        
        return userMapper.userToUserDTO(
                userRepo.findById(userId)
                        .orElseThrow(() -> new IDNotFoundException(User.class, userId))
        );
    }
    
    public List<UserDTO> getAll(){

        return userRepo.findAll().stream()
                .map(userMapper::userToUserDTO)
                .toList();
    }

	public UserDTO create(UserSignUpDTO signUpDTO) {
        if (userRepo.existsByUsername(signUpDTO.username())){
            throw new EntityAlreadyExistsException(User.class, signUpDTO.username());
        }
        if(!isValidPwd(signUpDTO.password())){
            throw new BusinessRuleException("Password does not meet the required criteria.");
        }
        User user = new User();
        user.setUsername(signUpDTO.username());
        user.setPassword(encoder.encode(signUpDTO.password()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User createdUser = userRepo.save(user);
		return userMapper.userToUserDTO(
                createdUser
        );
	}

    private boolean isValidPwd(String pwd){
        return pwd != null && pwd.length() > 6;
    }
	public boolean delete(Integer userId){
		if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return true;
        } else {
            throw new NullPointerException();
        }
	}
}
