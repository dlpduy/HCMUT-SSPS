package com.project.SSPS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
    @GetMapping("/student/hello")
    public String pageforStudent() {
        return "Hello World!";
    }

    @GetMapping("/spso/hello")
    public String pageforSPSO() {
        return "Hello World!";
    }

}
