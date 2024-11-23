package com.project.SSPS.service;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.response.*;

import java.util.List;

public interface ISpsoService {
    SpsoResponse findSpsoInfo(Long id);
    List<PrinterResponse> getAllPrinters();
    PrinterResponse getPrinterById(String printer_id);
    List<PrintResponse> getAllPrintRequests();
    List<PrintResponse> getAllPrintRequestsByPrinterId(String printer_id);
    List<PrintResponse> getAllPrintRequestsByStudentId(String std_id);
    OverallResponse getOverall();
    PrinterResponse createPrinter(PrinterDTO printerDTO);
    SemesterResponse newSemester(String semester);
}
