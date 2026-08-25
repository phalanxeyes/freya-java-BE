package com.salest.freya.mapper;

import com.salest.freya.dtos.song.CreateSongDTO;
import com.salest.freya.dtos.song.SongDTO;
import com.salest.freya.entities.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    Song createSongDTOToSong(CreateSongDTO dto);
    SongDTO songToSongDTO(Song song);
}
