package com.salest.freya.services;

import com.salest.freya.dtos.song.CreateSongDTO;
import com.salest.freya.dtos.song.SongDTO;
import com.salest.freya.entities.Song;
import com.salest.freya.exceptions.IDNotFoundException;
import com.salest.freya.mapper.SongMapper;
import com.salest.freya.repositories.SongRepository;
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

        Song song = songRepo.findById(songId).orElseThrow();
        System.out.println("album = " + (song.getAlbum() != null ? song.getAlbum().getId() : null));
        return songMapper.songToSongDTO(song);
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