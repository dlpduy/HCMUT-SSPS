package com.project.SSPS.controller;

import com.project.SSPS.dto.BuyPageDTO;
import com.project.SSPS.response.BaseResponse;
import com.project.SSPS.response.BuyPageResponse;
import com.project.SSPS.service.PaperService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("${api.prefix}/student")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private PaperService paperService;

    // Buy pages for a student
    @PostMapping("/page/create")
    public ResponseEntity<?> createPaymentforBuyPage(@RequestBody BuyPageDTO buyPageDTO, HttpServletRequest request) {
        try {

            return ResponseEntity.ok(paperService.createPayment(buyPageDTO, request));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // @PostMapping("/page")
    // public ResponseEntity<?> buyPages(@RequestBody BuyPageDTO buyPageDTO,
    // HttpServletRequest request) {
    // try {
    // paperService.buyPages(buyPageDTO, request);
    // return ResponseEntity.ok(new BuyPageResponse());
    // } catch (Exception e) {
    // return GlobalException.handleException(e);
    // }
    // }

    // Get page left
    @GetMapping("/page/{id}")
    public ResponseEntity<?> getPagesLeft(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(paperService.getPagesLeft(studentId));
    }

    // Get page-buying history
    @GetMapping("/pages/{id}")
    public ResponseEntity<?> getPageBuyingHistory(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(paperService.getPageBuyingHistory(studentId));
    }
}
