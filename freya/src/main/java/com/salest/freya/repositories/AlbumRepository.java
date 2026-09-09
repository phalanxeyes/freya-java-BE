package com.salest.freya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salest.freya.entities.Album;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
	
}