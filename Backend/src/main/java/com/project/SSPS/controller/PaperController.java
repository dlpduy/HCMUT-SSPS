package com.project.SSPS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SSPS.dto.PaperDTO;
import com.project.SSPS.service.PaperService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/spso")
public class PaperController {
    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @PostMapping("/paper")
    @ApiMessage("Create paper successfully")
    public ResponseEntity<?> create(@Valid @RequestBody PaperDTO paperDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(paperService.create(paperDTO, request));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @PutMapping("/paper/{id}")
    @ApiMessage("Update paper successfully")
    public ResponseEntity<?> update(@Valid @RequestBody PaperDTO paperDTO, @PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(paperService.update(paperDTO, id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @GetMapping("/paper")
    @ApiMessage("Get all papers")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(paperService.getAll());
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @GetMapping("/paper/{id}")
    @ApiMessage("Get paper by id")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(paperService.getById(id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @DeleteMapping("/paper/{id}")
    @ApiMessage("Delete paper by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(paperService.delete(id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

}
