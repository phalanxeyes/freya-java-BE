package com.example.freya.services;


import java.util.List;

import com.example.freya.dtos.cover.CoverDTO;
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
    
    public List<CoverDTO> getAll(){
		return coverRepository.findAll().stream()
				.map(coverMapper::coverToCoverDTO)
				.toList();
    }

	public CoverDTO create(CreateCoverDTO createDTO) {
		return coverMapper.coverToCoverDTO(
				coverRepository.save(coverMapper.createCoverDTOtoCover(createDTO))
		);
	}
	
	@Transactional
	public CoverDTO update(Integer id, Cover coverDetails) {
	    Cover existingCover = coverRepository.findById(id)
	            .orElseThrow(() -> new IDNotFoundException(Cover.class, id));

	    existingCover.setDuration(coverDetails.getDuration());
	    existingCover.setCreatedAt(coverDetails.getCreatedAt());

	    // If you're wondering: no repository.save() is needed! 
	    // Hibernate automatically updates the database when the transaction commits.
	    return coverMapper.coverToCoverDTO(existingCover);
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