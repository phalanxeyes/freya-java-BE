package com.example.freya.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.freya.entities.Cover;
import com.example.freya.repositories.CoverRepository;

@Service
public class CoverService {

    CoverRepository coverRepository;

    public Cover getCoverById(Integer coverId) {
        
        if (coverId == null) {
            throw new IllegalArgumentException("Invalid cover ID");
        }
        
        return coverRepository.findById(coverId).orElseThrow(null);
    }
    
    public List<Cover> getAll(){
    	return coverRepository.findAll();
    }

	public Cover crear(Cover cover) {
		
		return coverRepository.save(cover);
	}
}