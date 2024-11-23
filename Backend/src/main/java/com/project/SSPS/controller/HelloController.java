package com.project.SSPS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/v1")
@RestController
public class HelloController {
    @GetMapping("/student")
    public String pageforStudent() {
        return "Hello World!";
    }

    @GetMapping("api/v1/spso")
    public String pageforSPSO() {
        return "Hello World!";
    }

}
