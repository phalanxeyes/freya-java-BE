package com.example.freya.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.freya.entities.Cover;
import com.example.freya.services.CoverService;

@RestController
@RequestMapping("/api/covers")
public class CoverController {

	private final CoverService coverService = new CoverService();
	
    @GetMapping("/{coverId}")
    public Cover getCoverById(@PathVariable("coverId") Integer coverId) {
        return coverService.getCoverById(coverId);
    }
    
    @GetMapping
    public List<Cover> getAll(){
    	return coverService.getAll();
    }
    
    @PostMapping
    public Cover create(@RequestBody Cover cover) {
        return coverService.crear(cover);
    }
}
