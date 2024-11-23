package com.project.SSPS.service;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.model.Printer;
import com.project.SSPS.repository.PrinterRepository;
import com.project.SSPS.response.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SpsoService implements ISpsoService {
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
    public PrinterResponse getPrinterById(Long printer_id) {
        Printer printer = printerRepository.findById(printer_id).orElse(null);
        return PrinterResponse.fromPrinter(printer);
    }

    @Override
    public PrinterResponse updatePrinter(Long id, PrinterDTO entity) throws Exception {
        Printer printer = printerRepository.findById(id).orElse(null);
        if (printer == null) {
            throw new Exception("Printer not found");
        }
        printer.setBrand(entity.getBrand());
        printer.setModel(entity.getModel());
        printer.setLocation(entity.getLocation());
        printer.setStatus(entity.isStatus());
        return PrinterResponse.fromPrinter(printerRepository.save(printer));
    }

    @Override
    public Void deletePrinter(Long id) throws Exception {
        Printer printer = printerRepository.findById(id).orElse(null);
        if (printer == null) {
            throw new Exception("Printer not found");
        }
        printerRepository.delete(printer);
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
