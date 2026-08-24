package com.example.freya.dtos.user;

import java.time.LocalDateTime;

public record UserDTO(Integer id, String userName, LocalDateTime createdAt) {
}
