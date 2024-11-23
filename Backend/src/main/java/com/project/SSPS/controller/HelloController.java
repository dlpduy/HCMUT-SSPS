package com.project.SSPS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/student")
<<<<<<< HEAD
    public String student() {
        return "Hello World!";
    }

    @GetMapping("/spso")
    public String spso() {
=======
    public String pageforStudent() {
>>>>>>> ea80ce5c64d519eab60320799c9c968febdad827
        return "Hello World!";
    }

    @GetMapping("/spso")
    public String pageforSPSO() {
        return "Hello World!";
    }

}
