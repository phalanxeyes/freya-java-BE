package com.example.freya.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freya.entities.Cover;
import com.example.freya.entities.User;
import com.example.freya.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public User getById(Integer userId) {
        
        if (userId == null) {
            throw new IllegalArgumentException("Invalid cover ID");
        }
        
        return userRepo.findById(userId).orElseThrow(null);
    }
    
    public List<User> getAll(){
    	return userRepo.findAll();
    }

	public User create(User user) {
		
		return userRepo.save(user);
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
