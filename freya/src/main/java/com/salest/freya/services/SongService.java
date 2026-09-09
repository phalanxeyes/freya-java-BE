package com.salest.freya.services;

import com.salest.freya.dtos.cover.CoverDTO;
import com.salest.freya.dtos.song.CreateSongDTO;
import com.salest.freya.dtos.song.SongDTO;
import com.salest.freya.dtos.song.UpdateSongDTO;
import com.salest.freya.entities.Album;
import com.salest.freya.entities.Cover;
import com.salest.freya.entities.Song;
import com.salest.freya.exceptions.IDNotFoundException;
import com.salest.freya.mapper.SongMapper;
import com.salest.freya.repositories.AlbumRepository;
import com.salest.freya.repositories.CoverRepository;
import com.salest.freya.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired 
    private CoverRepository coverRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SongMapper songMapper;

    public SongDTO getById(Integer songId) {

        if (songId == null) {
            throw new IllegalArgumentException("Invalid song ID");
        }

        Song song = songRepository.findById(songId).orElseThrow();
        System.out.println("album = " + (song.getAlbum() != null ? song.getAlbum().getId() : null));
        return songMapper.songToSongDTO(song);
    }

    public List<SongDTO> getAll(){

        return songRepository.findAll().stream()
                .map(songMapper::songToSongDTO)
                .toList();
    }

    public SongDTO create(CreateSongDTO createDTO) {
        Song song = songMapper.createSongDTOToSong(createDTO);
        return songMapper.songToSongDTO(
                songRepository.save(song)
        );
    }
    
	@Transactional
	public SongDTO update(Integer id, UpdateSongDTO songDetails) {
	    Song existingSong = songRepository.findById(id)
	            .orElseThrow(() -> new IDNotFoundException(Cover.class, id));

	    if (songDetails.albumId() != null) {
	    	
	    	Album album = albumRepository.findById(songDetails.albumId())
	                .orElseThrow(() -> new IDNotFoundException(Album.class, songDetails.albumId()));
	    	
	    	existingSong.setAlbum(album);
	    }
	    
	    
	    existingSong.setDuration(songDetails.duration());
	    existingSong.setName(songDetails.name());
	    existingSong.setLyrics(songDetails.lyrics());

	    existingSong = songRepository.saveAndFlush(existingSong);
	    
	    return songMapper.songToSongDTO(existingSong);
	}

    
    @Transactional
	public void delete(Integer songId){
		songRepository.findById(songId).orElseThrow(() -> new IDNotFoundException(Song.class, songId));
		
	     // Delete all covers referencing this song first

	    coverRepository.deleteBySongId(songId);
	    
	    // Now the song can be safely deleted

	    songRepository.deleteById(songId); 
	}
}