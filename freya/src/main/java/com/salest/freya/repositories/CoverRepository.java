package com.salest.freya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salest.freya.entities.Cover;

public interface CoverRepository extends JpaRepository<Cover, Integer> {
	
}