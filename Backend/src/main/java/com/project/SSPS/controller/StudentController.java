package com.project.SSPS.controller;

import com.project.SSPS.dto.BuyPageDTO;
import com.project.SSPS.dto.*;
import com.project.SSPS.response.BaseResponse;
import com.project.SSPS.response.BuyPageResponse;
import com.project.SSPS.response.FileResponse;
import com.project.SSPS.response.PrintingLogResponse;
import com.project.SSPS.service.JwtService;
import com.project.SSPS.service.PaperService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/student")
@RequiredArgsConstructor
public class StudentController {
    private final PaperService paperService;

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
    @GetMapping("/page")
    public ResponseEntity<?> getPagesLeft(HttpServletRequest request) {
        return ResponseEntity.ok(paperService.getPagesLeft(request));
    }

    // Get page-buying history
    @GetMapping("/pages-history")
    public ResponseEntity<?> getPageBuyingHistory(HttpServletRequest request) {
        return ResponseEntity.ok(paperService.getPageBuyingHistory(request));
    }

    // Create print setting
    @PostMapping("print")
    public ResponseEntity<?> printDocument(HttpServletRequest request,
            @Valid @RequestBody PrintRequestDTO printRequest) {
        try {
            PrintingLogResponse printingLogResponse = paperService.printDocument(printRequest, request);
            return ResponseEntity.ok(printingLogResponse);
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // Create file
    @PostMapping("/file")
    public ResponseEntity<?> createFile(@Valid @RequestBody FileDTO fileDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(paperService.createFile(fileDTO, request));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // Get student pringting logs
    @GetMapping("/prints")
    public ResponseEntity<?> getPrintingLogs(HttpServletRequest request) {
        try {
            List<PrintingLogResponse> printingLogs = paperService.getStudentPrintingLogs(request);
            return ResponseEntity.ok(printingLogs);
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // Get student printing logs by printer id
    @GetMapping("print/{id}")
    public ResponseEntity<?> getPrinterLogs(@PathVariable("id") Long printerId, HttpServletRequest request) {
        try {
            List<PrintingLogResponse> printingLogs = paperService.getPrinterLogs(printerId, request);
            return ResponseEntity.ok(printingLogs);
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // Get student upload file
    @GetMapping("files")
    public ResponseEntity<?> getFiles(HttpServletRequest request) {
        try {
            List<FileResponse> files = paperService.getStudentFiles(request);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }
}
