package com.salest.freya.services;


import java.util.List;

import com.salest.freya.dtos.cover.CoverDTO;
import com.salest.freya.dtos.cover.CreateCoverDTO;
import com.salest.freya.dtos.cover.UpdateCoverDTO;
import com.salest.freya.exceptions.IDNotFoundException;
import com.salest.freya.mapper.CoverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salest.freya.entities.Cover;
import com.salest.freya.entities.Song;
import com.salest.freya.repositories.CoverRepository;
import com.salest.freya.repositories.SongRepository;

import jakarta.transaction.Transactional;

@Service
public class CoverService {
	@Autowired
    CoverRepository coverRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
    CoverMapper coverMapper;

    public CoverDTO getCoverById(Integer coverId) {
        
        if (coverId == null) {
            throw new IllegalArgumentException("Invalid cover ID");
        }
        
        return coverMapper.coverToCoverDTO(
				coverRepository.findById(coverId).orElseThrow(() -> new IDNotFoundException(Cover.class, coverId))
    		);
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
	public CoverDTO update(Integer id, UpdateCoverDTO coverDetails) {
	    Cover existingCover = coverRepository.findById(id)
	            .orElseThrow(() -> new IDNotFoundException(Cover.class, id));

	    if (coverDetails.songId() != null) {
	    	Song songProxy = songRepository.getReferenceById(coverDetails.songId());
	        existingCover.setSong(songProxy);
	    }
	    
	    if (coverDetails.youtubeUrl() != null && !coverDetails.youtubeUrl().isBlank()) {
		existingCover.setYoutubeUrl(coverDetails.youtubeUrl());}

	    if (coverDetails.duration() != null) {
	    existingCover.setDuration(coverDetails.duration());}
	    
	    
	    // If you're wondering: no repository.save() is needed! 
	    // Hibernate automatically updates the database when the transaction commits.
	    return coverMapper.coverToCoverDTO(existingCover);
	}

	
	public void delete(Integer coverId){
		coverRepository.findById(coverId).orElseThrow(() -> new IDNotFoundException(Cover.class, coverId));
		coverRepository.deleteById(coverId);
	}
}