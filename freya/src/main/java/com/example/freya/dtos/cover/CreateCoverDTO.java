package com.example.freya.dtos.cover;

import java.time.LocalDateTime;

public record CreateCoverDTO(Integer duration, LocalDateTime createdAt, Integer playCount){}
