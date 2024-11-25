package com.project.SSPS.service;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.response.*;

import java.util.List;

public interface ISpsoService {
    SpsoResponse findSpsoInfo(Long id);

    List<PrinterResponse> getAllPrinters();

    PrinterResponse getPrinterById(Long printer_id) throws Exception;

    PrinterResponse updatePrinter(Long id, PrinterDTO entity) throws Exception;

    Void deletePrinter(Long id) throws Exception;

    List<PrintResponse> getAllPrintRequests();

    List<PrintResponse> getAllPrintRequestsByPrinterId(String printer_id);

    List<PrintResponse> getAllPrintRequestsByStudentId(String std_id);

    OverallResponse getOverall();

    PrinterResponse createPrinter(PrinterDTO printerDTO);

    SemesterResponse newSemester(String semester);
}
