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

	private Cover getCoverEntityById(Integer coverId){
		if (coverId == null) {
			throw new IllegalArgumentException("Invalid cover ID");
		}
		return coverRepository.findById(coverId).orElseThrow(() -> new IDNotFoundException(Cover.class, coverId));
	}

    public CoverDTO getCoverById(Integer coverId) {
        return coverMapper.coverToCoverDTO(
				getCoverEntityById(coverId)
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
	    	
	    	Song song = songRepository.findById(coverDetails.songId())
	                .orElseThrow(() -> new IDNotFoundException(Song.class, coverDetails.songId()));
	    	
	    	existingCover.setSong(song);
	    }
	    
	    if (coverDetails.youtubeUrl() != null && !coverDetails.youtubeUrl().isBlank()) {
		existingCover.setYoutubeUrl(coverDetails.youtubeUrl());}

	    if (coverDetails.duration() != null) {
	    existingCover.setDuration(coverDetails.duration());}
	    
	    existingCover = coverRepository.saveAndFlush(existingCover);
	    
	    return coverMapper.coverToCoverDTO(existingCover);
	}

	
	public void delete(Integer coverId){
		coverRepository.findById(coverId).orElseThrow(() -> new IDNotFoundException(Cover.class, coverId));
		coverRepository.deleteById(coverId);
	}

	public List<CoverDTO> getAllApprovedStatus(){
		return coverRepository.findByStatus(Cover.Status.APPROVED).stream()
				.map(coverMapper::coverToCoverDTO)
				.toList();
	}

	public List<CoverDTO> getAllPendingStatus(){
		return coverRepository.findByStatus(Cover.Status.PENDING).stream()
				.map(coverMapper::coverToCoverDTO)
				.toList();
	}

	public CoverDTO approveCoverById(Integer coverId){
		Cover cover = getCoverEntityById(coverId);
		cover.setStatus(Cover.Status.APPROVED);
		return coverMapper.coverToCoverDTO(coverRepository.save(cover));
	}

	public CoverDTO rejectCoverById(Integer coverId){
		Cover cover = getCoverEntityById(coverId);
		cover.setStatus(Cover.Status.REJECTED);
		return coverMapper.coverToCoverDTO(coverRepository.save(cover));
	}
}