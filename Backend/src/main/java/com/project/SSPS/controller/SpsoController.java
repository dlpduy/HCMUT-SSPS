package com.project.SSPS.controller;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.response.*;
import com.project.SSPS.service.ISpsoService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("api/v1/spso/")
@RequiredArgsConstructor
public class SpsoController {
    private final ISpsoService spsoService;

    @PostMapping("printer")
    @ApiMessage("Create printer successfully")
    public ResponseEntity<?> createPrinter(@Valid @RequestBody PrinterDTO printerDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // return list default message
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok(spsoService.createPrinter(printerDTO));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // get all printer info
    // localhost:8080/api/v1/spso/printer?page=0&size=10
    @GetMapping("printer")
    @ApiMessage("Get all printers successfully")
    public ResponseEntity<?> getAllprinters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<PrinterResponse> printerResponses = spsoService.getAllPrinters(pageRequest);
            int totalPages = printerResponses.getTotalPages();
            return ResponseEntity.ok(new PrinterListResponse(printerResponses.getContent(), totalPages));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @GetMapping("printer/{printer_id}")
    @ApiMessage("Get printer by id successfully")
    public ResponseEntity<?> getPrinter(@PathVariable Long printer_id) {
        try {
            return ResponseEntity.ok(spsoService.getPrinterById(printer_id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @PutMapping("printer/{id}")
    @ApiMessage("Update printer successfully")
    public ResponseEntity<?> updatePrinter(@PathVariable Long id, @Valid @RequestBody PrinterDTO entity) {
        try {
            return ResponseEntity.ok(spsoService.updatePrinter(id, entity));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @DeleteMapping("printer/{id}")
    public ResponseEntity<?> deletePrinter(@PathVariable Long id) {
        try {

            return ResponseEntity.ok().body(spsoService.deletePrinter(id));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    // get all printing history
    // http://localhost:8386/api/v1/spso/print?page=0&size=10
    @GetMapping("print")
    public ResponseEntity<?> getAllPrintRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<PrintingLogResponse> printingLogResponses = spsoService.getAllPrintingLogs(pageRequest);
            int totalPages = printingLogResponses.getTotalPages();
            return ResponseEntity.ok(new PrintingLogListResponse(printingLogResponses.getContent(), totalPages));
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }

    @GetMapping("page")
    public ResponseEntity<?> getAllPurchasedPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
            PaymentLogsListResponse paymentLogsListResponses = spsoService.getAllPaymentLogs(pageRequest);
            return ResponseEntity.ok(paymentLogsListResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("statistic")
    public ResponseEntity<?> getStastics() {
        try {
            StatisticResponse statisticResponse = spsoService.getStatistic();
            return ResponseEntity.ok(statisticResponse);
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }
}
