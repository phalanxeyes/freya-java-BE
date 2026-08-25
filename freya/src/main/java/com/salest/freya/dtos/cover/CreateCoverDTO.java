package com.salest.freya.dtos.cover;

import java.time.LocalDateTime;

public record CreateCoverDTO(Integer duration, Integer songId, Integer uploaderUserId, String youtubeUrl){}
