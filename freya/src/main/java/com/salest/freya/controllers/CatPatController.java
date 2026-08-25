package com.salest.freya.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatPatController {

    @GetMapping("/catpat")
    public String catpat() {
        return "Catete Patete!";
    }
}