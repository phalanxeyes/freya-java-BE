package com.example.freya.mapper;

import com.example.freya.dtos.song.CreateSongDTO;
import com.example.freya.dtos.song.SongDTO;
import com.example.freya.entities.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    Song createSongDTOToSong(CreateSongDTO dto);
    SongDTO songToSongDTO(Song song);
}
