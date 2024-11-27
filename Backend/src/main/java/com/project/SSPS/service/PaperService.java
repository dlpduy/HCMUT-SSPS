package com.project.SSPS.service;

import com.project.SSPS.dto.*;
import com.project.SSPS.model.*;
import com.project.SSPS.repository.*;
import com.project.SSPS.response.FileResponse;
import com.project.SSPS.response.OrderHistoryResponse;
import com.project.SSPS.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


import com.project.SSPS.model.Paper;
import com.project.SSPS.repository.PaperRepository;
import com.project.SSPS.response.PaperResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaperService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PaperRepository paperRepository;
    private final OrderRepository orderRepository;
    private final OrderPaperRepository orderPaperRepository;
    private final HttpServletRequest httpServletRequest;
    private final StudentPaperRepository studentPaperRepository;
    private final PrintingLogRepository printingLogRepository;
    private final PrinterRepository printerRepository;
    private final FileRepository fileRepository;

    public PaperService(UserService userService,
                        JwtService jwtService,
                        PaperRepository paperRepository,
                        OrderRepository orderRepository,
                        OrderPaperRepository orderPaperRepository,
                        HttpServletRequest httpServletRequest,
                        StudentPaperRepository studentPaperRepository,
                        PrinterRepository printerRepository,
                        PrintingLogRepository printingLogRepository,
                        FileRepository fileRepository
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.paperRepository = paperRepository;
        this.orderRepository = orderRepository;
        this.orderPaperRepository = orderPaperRepository;
        this.httpServletRequest = httpServletRequest;
        this.studentPaperRepository = studentPaperRepository;
        this.printerRepository = printerRepository;
        this.printingLogRepository = printingLogRepository;
        this.fileRepository = fileRepository;
    }
    @Transactional
    public void buyPages(Long studentId, BuyPageDTO request) {

    }

    public PaperResponse create(PaperDTO paperDTO, HttpServletRequest request) {
        Paper paper = new Paper();
        if (paperRepository.existsByType(paperDTO.getType())) {
            throw new RuntimeException("Paper already exists");
        }

        paper.setType(paperDTO.getType());
        paper.setWidth(paperDTO.getWidth());
        paper.setHeight(paperDTO.getHeight());
        paper.setPrice(paperDTO.getPrice());
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        paper.setUser(userService.getUserbyUsername(jwtService.extractUsername(token)));
        paperRepository.save(paper);
        return PaperResponse.fromPaper(paper);
    }


    public PaperResponse update(PaperDTO paperDTO, Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }

        paper.setType(paperDTO.getType());
        paper.setWidth(paperDTO.getWidth());
        paper.setHeight(paperDTO.getHeight());
        paper.setPrice(paperDTO.getPrice());
        paperRepository.save(paper);
        return PaperResponse.fromPaper(paper);
    }

    public List<PageResponse> getPagesLeft(Long studentId){
            List<StudentPaper> studentPapers = studentPaperRepository.findByStudentId(studentId);
            List<PageResponse> responses = new ArrayList<>();

            for(StudentPaper studentPaper : studentPapers) {
                PageResponse pageResponse = new PageResponse();
                pageResponse.setPaperType(studentPaper.getPaperType());
                pageResponse.setQuantity(studentPaper.getQuantity());
                responses.add(pageResponse);
            }
            return responses;
        }
    public PaperResponse getById(Long id) throws Exception {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new Exception("Paper not found");
        }

        return PaperResponse.fromPaper(paper);
    }

    public List<OrderHistoryResponse> getPageBuyingHistory(Long studentId) {
        List<Order> orders = orderRepository.findByStudentIdOrderByTimeDesc(studentId);
        List<OrderHistoryResponse> responses = new ArrayList<>();

        for (Order order : orders) {
            OrderHistoryResponse response = new OrderHistoryResponse();
            response.setOrderId(order.getId());
            response.setTotalPrice(order.getTotalPrice());
            response.setTime(order.getTime());

            List<OrderPaper> orderPapers = orderPaperRepository.findByOrderId(order.getId());
            List<OrderHistoryResponse.OrderPaperDetail> paperDetails = new ArrayList<>();

            for (OrderPaper orderPaper : orderPapers) {
                OrderHistoryResponse.OrderPaperDetail detail = new OrderHistoryResponse.OrderPaperDetail();
                detail.setPaperType(orderPaper.getPaperType());
                detail.setQuantity(orderPaper.getQuantity());
                paperDetails.add(detail);
            }

            response.setPapers(paperDetails);
            responses.add(response);
        }

        return responses;
    }
    public List<PaperResponse> getAll() {
        return paperRepository.findAll().stream().map(PaperResponse::fromPaper).toList();
    }

    // POST api/v1/student/print
    public void printDocument(PrintRequestDTO request, HttpServletRequest httpRequest) {
        // Get user id from token
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        Printer printer = printerRepository.findById(request.getPrinterId())
                .orElseThrow(() -> new RuntimeException("Printer not found"));

        Paper paper = paperRepository.findByType(request.getPaperType())
                .orElseThrow(() -> new RuntimeException("Invalid paper type"));

        File file = fileRepository.findById(request.getFileId())
                .orElseThrow(() -> new RuntimeException("File not found"));

        StudentPaper studentPaper = studentPaperRepository
                .findByStudentIdAndPaperType(studentId, request.getPaperType());

        if (studentPaper == null || studentPaper.getQuantity() < request.getNumPages()) {
            throw new RuntimeException("Insufficient pages available");
        }

        if (request.getPrintingPages().isEmpty() || request.getPrintingPages().length() > 100) {
            throw new RuntimeException("Invalid printing pages format");
        }

        PrintingLog printingLog = new PrintingLog();
        printingLog.setPrinter(printer);
        printingLog.setPaper(paper);
        printingLog.setFile(file);
        printingLog.setNumCopy(request.getNumCopy());
        printingLog.setSided(request.getSided());
        printingLog.setPrintingPages(request.getPrintingPages());
        printingLog.setNumPages(request.getNumPages());
        printingLog.setTime(LocalDateTime.now());

        printingLogRepository.save(printingLog);

        studentPaper.setQuantity(studentPaper.getQuantity() - request.getNumPages());
        studentPaperRepository.save(studentPaper);
    }

    public FileResponse createFile(FileDTO fileDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        User student = userService.findById(userId);

        File file = new File();
        file.setFileName(fileDTO.getFileName());
        file.setFileType(fileDTO.getFileType());
        file.setStudent(student);

        file = fileRepository.save(file);

        FileResponse response = new FileResponse();
        response.setFileName(file.getFileName());
        response.setFileType(file.getFileType());
        return response;
    }

    public String delete(Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }
        paperRepository.deleteById(id);
        return "Paper deleted successfully";
    }
}
