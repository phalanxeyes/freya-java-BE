package com.example.freya.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freya.entities.Cover;
import com.example.freya.repositories.CoverRepository;

import jakarta.transaction.Transactional;

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
	
	@Transactional
	public Cover update(Integer id, Cover coverDetails) {
	    Cover existingCover = coverRepository.findById(id)
	            .orElseThrow(() -> new NullPointerException());

	    existingCover.setDuracion(coverDetails.getDuracion());
	    existingCover.setFechaHora(coverDetails.getFechaHora());

	    // If you're wondering: no repository.save() is needed! 
	    // Hibernate automatically updates the database when the transaction commits.
	    return existingCover; 
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