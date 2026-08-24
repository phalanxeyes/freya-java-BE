package com.example.freya.dtos.cover;

import java.time.LocalDateTime;

public record CoverDTO(Integer id, Integer duration, LocalDateTime createdAt, Integer playCount) {
}
