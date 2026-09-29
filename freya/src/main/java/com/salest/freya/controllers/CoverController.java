package com.salest.freya.controllers;

import java.util.List;

import com.salest.freya.dtos.cover.CoverDTO;
import com.salest.freya.dtos.cover.CreateCoverDTO;
import com.salest.freya.dtos.cover.UpdateCoverDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.salest.freya.entities.Cover;
import com.salest.freya.services.CoverService;

@RestController
@RequestMapping("/api/covers")
public class CoverController {

	@Autowired
	private CoverService coverService;
	
    @GetMapping("/{coverId}")
    public CoverDTO getCoverById(@PathVariable("coverId") Integer coverId) {
        return coverService.getCoverById(coverId);
    }

    // default -> solo los aprobados
    @GetMapping({"", "approved"})
    public List<CoverDTO> getAllApproved(){
        return coverService.getAllApprovedStatus();
    }

    @GetMapping("/pending")
    public List<CoverDTO> getAllPending() {
        return coverService.getAllPendingStatus();
    }

    @GetMapping("/all")
    public List<CoverDTO> getAll(){
        return coverService.getAll();
    }

    @PostMapping("{coverId}/approve")
    public CoverDTO approveCover(@PathVariable("coverId") Integer coverId){
        return coverService.approveCoverById(coverId);
    }

    @PostMapping("{coverId}/reject")
    public CoverDTO rejectCover(@PathVariable("coverId") Integer coverId){
        return coverService.rejectCoverById(coverId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoverDTO create(@RequestBody CreateCoverDTO createCoverDTO) {
        return coverService.create(createCoverDTO);
    }
    
    @PutMapping("/{coverId}")
    public CoverDTO update(@PathVariable("coverId") Integer coverId, @RequestBody UpdateCoverDTO coverDetails){
    	return coverService.update(coverId, coverDetails);
    }
        
    @DeleteMapping("/{coverId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Integer coverId) {
        coverService.delete(coverId);
        return ResponseEntity.noContent().build();
    }
}
