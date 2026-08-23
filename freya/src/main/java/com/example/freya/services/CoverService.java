package com.example.freya.services;


import java.util.List;

import com.example.freya.dtos.cover.CreateCoverDTO;
import com.example.freya.exceptions.IDNotFoundException;
import com.example.freya.mapper.CoverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freya.entities.Cover;
import com.example.freya.repositories.CoverRepository;

import jakarta.transaction.Transactional;

@Service
public class CoverService {
	@Autowired
    CoverRepository coverRepository;
	@Autowired
    CoverMapper coverMapper;

    public Cover getCoverById(Integer coverId) {
        
        if (coverId == null) {
            throw new IllegalArgumentException("Invalid cover ID");
        }
        
        return coverRepository.findById(coverId).orElseThrow(() -> new IDNotFoundException(Cover.class, coverId));
    }
    
    public List<Cover> getAll(){
    	return coverRepository.findAll();
    }

	public Cover create(CreateCoverDTO createDTO) {
		return coverRepository.save(coverMapper.createCoverDTOtoCover(createDTO));
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