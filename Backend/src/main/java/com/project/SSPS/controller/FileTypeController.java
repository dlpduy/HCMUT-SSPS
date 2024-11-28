package com.project.SSPS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SSPS.dto.FileTypeDTO;
import com.project.SSPS.service.FileTypeService;
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
public class FileTypeController {
    private final FileTypeService fileTypeService;

    public FileTypeController(FileTypeService fileTypeService) {
        this.fileTypeService = fileTypeService;
    }

    @PostMapping("/filetype")
    @ApiMessage("Create file successfully")
    public ResponseEntity<?> create(@Valid @RequestBody FileTypeDTO fileTypeDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(fileTypeService.create(fileTypeDTO, request));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @GetMapping("/filetype")
    @ApiMessage("Get all file types")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(fileTypeService.getAll());
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @GetMapping("/filetype/{id}")
    @ApiMessage("Get file type by id successfully")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(fileTypeService.getById(id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @PutMapping("/filetype/{id}")
    @ApiMessage("Update file type successfully")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody FileTypeDTO fileTypeDTO) {
        try {
            return ResponseEntity.ok().body(fileTypeService.update(id, fileTypeDTO));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @DeleteMapping("/filetype/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(fileTypeService.delete(id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

}
