package com.salest.freya.dtos.config;

public record UserPasswordValidationsDTO(boolean required, Integer min, Integer max) {}
