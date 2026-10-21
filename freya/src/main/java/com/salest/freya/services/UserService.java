package com.salest.freya.services;

import java.time.LocalDateTime;
import java.util.List;

import com.salest.freya.dtos.config.UserPasswordValidationsDTO;
import com.salest.freya.dtos.config.UserUsernameValidationsDTO;
import com.salest.freya.dtos.user.CreateUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.dtos.user.UserSignInDTO;
import com.salest.freya.dtos.user.UserSignUpDTO;
import com.salest.freya.exceptions.BusinessRuleException;
import com.salest.freya.exceptions.EntityAlreadyExistsException;
import com.salest.freya.exceptions.IDNotFoundException;
import com.salest.freya.exceptions.InvalidCredentialsException;
import com.salest.freya.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
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
    private ValidationsService validationsService;
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
        // throw if invalid
        validatePwd(signUpDTO.password());
        validateUsername(signUpDTO.username());
        //
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

    public boolean delete(Integer userId){
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return true;
        } else {
            throw new NullPointerException();
        }
    }

    private void validatePwd(String pwd){
        UserPasswordValidationsDTO pwdVal = validationsService.getPasswordValidations();
        if(pwd == null || pwd.isEmpty()){
            throw new BusinessRuleException("Password is empty");
        }
        if(pwd.length() < pwdVal.min()){
            throw new BusinessRuleException("Password does not meet the required criteria: Password too short");
        }
        if(pwd.length() > pwdVal.max()){
            throw new BusinessRuleException("Password does not meet the required criteria: Password too long");
        }
    }

    private void validateUsername(String username) {
        UserUsernameValidationsDTO userVal = validationsService.getUsernameValidations();
        if(username == null || username.isEmpty()){
            throw new BusinessRuleException("Username is empty");
        }
        if(username.length() > userVal.min()){
            throw new BusinessRuleException("Username does not meet the required criteria: Username too long");
        }
        if(username.length() < userVal.min()){
            throw new BusinessRuleException("Username does not meet the required criteria: Username too short");
        }
    }
}
