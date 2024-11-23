package com.project.SSPS.service;

import com.project.SSPS.model.Printer;
import com.project.SSPS.repository.PrinterRepository;
import com.project.SSPS.response.*;

import java.util.Date;
import java.util.List;

public class SpsoService implements ISpsoService{
    PrinterRepository printerRepository;
    @Override
    public SpsoResponse findSpsoInfo(Long id) {
        return null;
    }

    @Override
    public List<PrinterResponse> getAllPrinters() {
        List<Printer> printers = printerRepository.findAll();

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
