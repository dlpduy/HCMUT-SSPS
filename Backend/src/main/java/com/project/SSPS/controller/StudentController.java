package com.project.SSPS.controller;

import com.project.SSPS.dto.BuyPageDTO;
import com.project.SSPS.dto.*;
import com.project.SSPS.response.BaseResponse;
import com.project.SSPS.service.JwtService;
import com.project.SSPS.service.PaperService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("${api.prefix}/student/")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private PaperService paperService;

    // Buy pages for a student
//    @PostMapping("page/{id}")
//    public ResponseEntity<?> buyPages(@PathVariable("id") Long studentId, @RequestBody BuyPageDTO request) {
//        paperService.buyPages(studentId, request);
//        return ResponseEntity.ok().build();
//    }

    // Get page left
    @GetMapping("page/{id}")
    public ResponseEntity<?> getPagesLeft(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(paperService.getPagesLeft(studentId));
    }

    // Get page-buying history
    @GetMapping("pages/{id}")
    public ResponseEntity<?> getPageBuyingHistory(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(paperService.getPageBuyingHistory(studentId));
    }

    // Create print setting
    @PostMapping("print")
    public ResponseEntity<?> printDocument(HttpServletRequest request, @RequestBody PrintRequestDTO printRequest) {
            paperService.printDocument(printRequest, request);
            return ResponseEntity.ok().build();
    }

    // Upload file
    @PostMapping("file")
    public ResponseEntity<?> createFile(@RequestBody FileDTO fileDTO, HttpServletRequest request) {
        return ResponseEntity.ok(paperService.createFile(fileDTO, request));
    }
}
