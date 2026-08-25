package com.salest.freya.mapper;

import com.salest.freya.dtos.albums.AlbumDTO;
import com.salest.freya.dtos.albums.CreateAlbumDTO;
import com.salest.freya.entities.Album;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    Album createAlbumDTOtoAlbum(CreateAlbumDTO dto);
    AlbumDTO albumToAlbumDTO(Album album);
}
