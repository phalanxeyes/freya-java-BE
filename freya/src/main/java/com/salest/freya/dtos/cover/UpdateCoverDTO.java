package com.salest.freya.dtos.cover;

public record UpdateCoverDTO(
    String youtubeUrl,
    Integer duration,
    Integer songId
) {}