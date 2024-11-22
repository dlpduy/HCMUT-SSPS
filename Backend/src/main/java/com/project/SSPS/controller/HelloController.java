package com.project.SSPS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/student")
    public String student() {
        return "Hello World!";
    }

    @GetMapping("/spso")
    public String spso() {
        return "Hello World!";
    }
}
