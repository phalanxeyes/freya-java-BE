package com.salest.freya.dtos.user;

import java.time.LocalDateTime;

public record UserDTO(Integer id, String userName, LocalDateTime createdAt) {
}
