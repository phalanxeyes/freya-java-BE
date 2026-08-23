package com.example.freya.dtos.cover;

import java.time.LocalDateTime;

public record CoverDTO(Integer id, Integer duracion, LocalDateTime fechaHora, Integer reproducciones) {
}
