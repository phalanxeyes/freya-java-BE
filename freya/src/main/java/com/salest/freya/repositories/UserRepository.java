package com.salest.freya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salest.freya.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}