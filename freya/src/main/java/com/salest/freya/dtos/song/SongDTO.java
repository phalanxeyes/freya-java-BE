package com.salest.freya.dtos.song;

import com.salest.freya.dtos.albums.AlbumDTO;

public record SongDTO(Integer id, float duration, String name, String lyrics, AlbumDTO album) {
}
