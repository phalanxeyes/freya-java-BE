package com.example.freya.services;

import com.example.freya.dtos.song.CreateSongDTO;
import com.example.freya.dtos.song.SongDTO;
import com.example.freya.entities.Song;
import com.example.freya.exceptions.IDNotFoundException;
import com.example.freya.mapper.SongMapper;
import com.example.freya.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepo;
    @Autowired
    private SongMapper songMapper;

    public SongDTO getById(Integer songId) {

        if (songId == null) {
            throw new IllegalArgumentException("Invalid cover ID");
        }

        return songMapper.songToSongDTO(
                songRepo.findById(songId)
                        .orElseThrow(() -> new IDNotFoundException(Song.class, songId))
        );
    }

    public List<SongDTO> getAll(){

        return songRepo.findAll().stream()
                .map(songMapper::songToSongDTO)
                .toList();
    }

    public SongDTO create(CreateSongDTO createDTO) {
        Song song = songMapper.createSongDTOToSong(createDTO);
        return songMapper.songToSongDTO(
                songRepo.save(song)
        );
    }

    public boolean delete(Integer songId){
        if (songRepo.existsById(songId)) {
            songRepo.deleteById(songId);
            return true;
        } else {
            throw new NullPointerException();
        }
    }
}