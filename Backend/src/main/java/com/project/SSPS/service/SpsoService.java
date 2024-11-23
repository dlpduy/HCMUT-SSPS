package com.project.SSPS.service;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.model.Printer;
import com.project.SSPS.repository.PrinterRepository;
import com.project.SSPS.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class SpsoService implements ISpsoService{
    private final PrinterRepository printerRepository;
    @Override
    public SpsoResponse findSpsoInfo(Long id) {
        return null;
    }

    @Override
    public List<PrinterResponse> getAllPrinters() {
        List<Printer> printers = printerRepository.findAll();
        return printers
                .stream()
                .map(PrinterResponse::fromPrinter)
                .collect(Collectors.toList());
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
    public PrinterResponse createPrinter(PrinterDTO printerDTO) {
        Printer printer = Printer.builder()
                .brand(printerDTO.getBrand())
                .model(printerDTO.getModel())
                .location(printerDTO.getLocation())
                .status(printerDTO.isStatus())
                .build();

        return PrinterResponse.fromPrinter(printerRepository.save(printer));
    }

    @Override
    public SemesterResponse newSemester(String semester) {
        return null;
    }
}
