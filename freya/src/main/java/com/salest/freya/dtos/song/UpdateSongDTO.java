package com.salest.freya.dtos.song;

public record UpdateSongDTO(
    Integer duration,
    String name,
    String lyrics,
    Integer albumId
) {}