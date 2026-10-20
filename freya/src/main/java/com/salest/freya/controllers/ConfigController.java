package com.salest.freya.controllers;

import com.salest.freya.dtos.config.UserValidationsDTO;
import com.salest.freya.services.ValidationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/configs")
public class ConfigController {
    private final ValidationsService validationsService;

    public ConfigController(ValidationsService validationsService) {
        this.validationsService = validationsService;
    }

    @GetMapping("/user")
    public UserValidationsDTO getUserValidations() {
        return validationsService.getUserValidations();

    }
}
