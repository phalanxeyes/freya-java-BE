package com.example.freya.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freya.entities.Cover;
import com.example.freya.repositories.CoverRepository;

@Service
public class CoverService {
	@Autowired
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

	public Cover create(Cover cover) {
		
		return coverRepository.save(cover);
	}
	
	public boolean delete(Integer coverId){
		if (coverRepository.existsById(coverId)) {
            coverRepository.deleteById(coverId);
            return true;
        } else {
            throw new NullPointerException();
        }
	}
}