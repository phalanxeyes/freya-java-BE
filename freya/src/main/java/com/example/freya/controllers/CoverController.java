package com.example.freya.controllers;

import java.util.List;

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
    public List<Cover> getAll(){
    	return coverService.getAll();
    }


    @PostMapping
    public Cover create(@RequestBody Cover cover) {
        System.out.println(String.valueOf(cover.getClass()));
        return coverService.create(cover);
    }
    
    @PutMapping("/{coverId}")
    public Cover update(@PathVariable("coverId") Integer coverId, @RequestBody Cover coverDetails){
    	return coverService.update(coverId, coverDetails);
    }
    
    @DeleteMapping("/{coverId}")
    public boolean delete(@PathVariable Integer coverId){
    	return coverService.delete(coverId);
    }
}
