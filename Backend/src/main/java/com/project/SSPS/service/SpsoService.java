package com.project.SSPS.service;

import com.project.SSPS.response.*;

import java.util.Date;
import java.util.List;

public class SpsoService implements ISpsoService{
    @Override
    public SpsoResponse findSpsoInfo(String id) {
        return null;
    }

    @Override
    public List<PrinterResponse> getAllPrinters() {
        return List.of();
    }

    @Override
    public PrinterResponse getPrinterById(String printer_id) {
        return null;
    }

    @Override
    public List<PrintResponse> getAllPrintRequests() {
        return List.of();
    }

    @Override
    public List<PrintResponse> getAllPrintRequestsByPrinterId(String printer_id) {
        return List.of();
    }

    @Override
    public List<PrintResponse> getAllPrintRequestsByStudentId(String std_id) {
        return List.of();
    }

    @Override
    public OverallResponse getOverall() {
        return null;
    }

    @Override
    public PrinterResponse insertNewPrinter(String building, String model, Date importDate) {
        return null;
    }

    @Override
    public SemesterResponse newSemester(String semester) {
        return null;
    }
}
