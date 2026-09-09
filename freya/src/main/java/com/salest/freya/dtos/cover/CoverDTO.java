package com.salest.freya.dtos.cover;

import com.salest.freya.dtos.song.SongDTO;

import java.time.LocalDateTime;

public record CoverDTO(Integer id, Integer duration, Integer playCount, SongDTO song, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
