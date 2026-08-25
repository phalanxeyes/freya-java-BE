package com.example.freya.dtos.cover;

import com.example.freya.dtos.song.SongDTO;

import java.time.LocalDateTime;

public record CoverDTO(Integer id, Integer duration, LocalDateTime createdAt, Integer playCount, SongDTO song) {
}
