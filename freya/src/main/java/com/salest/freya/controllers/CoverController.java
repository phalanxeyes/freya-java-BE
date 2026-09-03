package com.salest.freya.controllers;

import java.util.List;

import com.salest.freya.dtos.cover.CoverDTO;
import com.salest.freya.dtos.cover.CreateCoverDTO;
import com.salest.freya.dtos.cover.UpdateCoverDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    
    @GetMapping
    public List<CoverDTO> getAll(){
    	return coverService.getAll();
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
