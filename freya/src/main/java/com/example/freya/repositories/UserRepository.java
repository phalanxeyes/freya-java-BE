package com.example.freya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.freya.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}