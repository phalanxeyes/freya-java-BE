package com.salest.freya.controllers;

import com.salest.freya.dtos.song.SongDTO;
import com.salest.freya.entities.Song;
import com.salest.freya.dtos.song.CreateSongDTO;
import com.salest.freya.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping("/{songId}")
    public SongDTO getSongById(@PathVariable("songId") Integer songId) {
        return songService.getById(songId);
    }

    @GetMapping
    public List<SongDTO> getAll(){
        return songService.getAll();
    }


    @PostMapping
    public SongDTO create(@RequestBody CreateSongDTO createSongDTO) {
        return songService.create(createSongDTO);
    }

    
    @PutMapping("/{songId}")
    public SongDTO update(@PathVariable("songId") Integer songId, @RequestBody Song songDetails){
        return songService.update(songId, songDetails);
    }
     
    
    @DeleteMapping("/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Integer songId) {
        songService.delete(songId);
        return ResponseEntity.noContent().build();
    }
}
