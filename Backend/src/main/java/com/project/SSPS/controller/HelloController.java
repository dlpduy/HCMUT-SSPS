package com.project.SSPS.controller;

import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Global;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SSPS.util.errors.GlobalException;

@RequestMapping("/api/v1")
@RestController
public class HelloController {
    @GetMapping("/student")
    public String pageforStudent() {
        return "Hello World!";
    }

    @GetMapping("/spso")
    public ResponseEntity<?> pageforSpso() {
        try {
            return ResponseEntity.ok("Hello Spso!");
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }

    }

}
