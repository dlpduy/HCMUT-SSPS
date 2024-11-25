package com.project.SSPS.service;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ISpsoService {
    PrinterResponse createPrinter(PrinterDTO printerDTO);

    SpsoResponse findSpsoInfo(Long id);

    Page<PrinterResponse> getAllPrinters(PageRequest pageRequest);

    PrinterResponse getPrinterById(Long printer_id) throws Exception;

    PrinterResponse updatePrinter(Long id, PrinterDTO entity) throws Exception;

    Void deletePrinter(Long id) throws Exception;

    List<PrintResponse> getAllPrintRequests();

    List<PrintResponse> getAllPrintRequestsByPrinterId(String printer_id);

    List<PrintResponse> getAllPrintRequestsByStudentId(String std_id);

    OverallResponse getOverall();

    SemesterResponse newSemester(String semester);
}
