package com.salest.freya.services;

import com.salest.freya.dtos.cover.CoverDTO;
import com.salest.freya.dtos.song.CreateSongDTO;
import com.salest.freya.dtos.song.SongDTO;
import com.salest.freya.entities.Cover;
import com.salest.freya.entities.Song;
import com.salest.freya.exceptions.IDNotFoundException;
import com.salest.freya.mapper.SongMapper;
import com.salest.freya.repositories.CoverRepository;
import com.salest.freya.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepo;
    @Autowired 
    private CoverRepository coverRepo;
    @Autowired
    private SongMapper songMapper;

    public SongDTO getById(Integer songId) {

        if (songId == null) {
            throw new IllegalArgumentException("Invalid song ID");
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
    
	@Transactional
	public SongDTO update(Integer id, Song songDetails) {
	    Song existingSong = songRepo.findById(id)
	            .orElseThrow(() -> new IDNotFoundException(Cover.class, id));

	    existingSong.setDuration(songDetails.getDuration());
	    existingSong.setName(songDetails.getName());
	    existingSong.setLyrics(songDetails.getLyrics());
	    
	    // If you're wondering: no repository.save() is needed! 
	    // Hibernate automatically updates the database when the transaction commits.
	    return songMapper.songToSongDTO(existingSong);
	}

    
    @Transactional
	public void delete(Integer songId){
		songRepo.findById(songId).orElseThrow(() -> new IDNotFoundException(Song.class, songId));
		
	     // Delete all covers referencing this song first

	    coverRepo.deleteBySongId(songId);
	    
	    // Now the song can be safely deleted

	    songRepo.deleteById(songId); 
	}
}