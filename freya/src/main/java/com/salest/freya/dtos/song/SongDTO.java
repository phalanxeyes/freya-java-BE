package com.salest.freya.dtos.song;

import java.time.LocalDateTime;

import com.salest.freya.dtos.albums.AlbumDTO;

public record SongDTO(Integer id, String name, float duration, String lyrics, AlbumDTO album, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
