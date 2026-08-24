package com.example.freya.services;

import java.time.LocalDateTime;
import java.util.List;

import com.example.freya.dtos.user.CreateUserDTO;
import com.example.freya.dtos.user.UserDTO;
import com.example.freya.exceptions.IDNotFoundException;
import com.example.freya.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freya.entities.User;
import com.example.freya.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
    @Autowired
    private UserMapper userMapper;
	
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

	public UserDTO create(CreateUserDTO createDTO) {
        User user = userMapper.createUserDTOToUser(createDTO);
        user.setCreatedAt(LocalDateTime.now());
        // TODO : HASH PWD
		return userMapper.userToUserDTO(
                userRepo.save(user)
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
}
