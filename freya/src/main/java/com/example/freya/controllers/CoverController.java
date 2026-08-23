package com.example.freya.controllers;

import java.util.List;

import com.example.freya.dtos.cover.CoverDTO;
import com.example.freya.dtos.cover.CreateCoverDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.freya.entities.Cover;
import com.example.freya.services.CoverService;

@RestController
@RequestMapping("/api/covers")
public class CoverController {

	@Autowired
	private CoverService coverService;
	
    @GetMapping("/{coverId}")
    public Cover getCoverById(@PathVariable("coverId") Integer coverId) {
        return coverService.getCoverById(coverId);
    }
    
    @GetMapping
    public List<CoverDTO> getAll(){
    	return coverService.getAll();
    }


    @PostMapping
    public CoverDTO create(@RequestBody CreateCoverDTO createCoverDTO) {
        return coverService.create(createCoverDTO);
    }
    
    @PutMapping("/{coverId}")
    public CoverDTO update(@PathVariable("coverId") Integer coverId, @RequestBody Cover coverDetails){
    	return coverService.update(coverId, coverDetails);
    }
    
    @DeleteMapping("/{coverId}")
    public boolean delete(@PathVariable Integer coverId){
    	return coverService.delete(coverId);
    }
}
