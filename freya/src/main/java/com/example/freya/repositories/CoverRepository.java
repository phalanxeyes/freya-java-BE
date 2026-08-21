package com.example.freya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.freya.entities.Cover;

public interface CoverRepository extends JpaRepository<Cover, Integer> {
	
}