package com.project.SSPS.service;

import com.project.SSPS.response.*;

import java.util.Date;
import java.util.List;

public interface ISpsoService {
    SpsoResponse findSpsoInfo(String id);
    List<PrinterResponse> getAllPrinters();
    PrinterResponse getPrinterById(String printer_id);
    List<PrintResponse> getAllPrintRequests();
    List<PrintResponse> getAllPrintRequestsByPrinterId(String printer_id);
    List<PrintResponse> getAllPrintRequestsByStudentId(String std_id);
    OverallResponse getOverall();
    PrinterResponse insertNewPrinter(String building, String model, Date importDate);
    SemesterResponse newSemester(String semester);
}
