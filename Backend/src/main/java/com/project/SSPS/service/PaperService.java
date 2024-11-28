package com.project.SSPS.service;

import com.project.SSPS.dto.*;
import com.project.SSPS.model.*;
import com.project.SSPS.repository.*;
import com.project.SSPS.response.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final PaymentService paymentService;
    private final PrintingLogRepository printingLogRepository;
    private final PrinterRepository printerRepository;
    private final FileRepository fileRepository;
    private final FileTypeRepository fileTypeRepository;

    public PaperService(
            UserService userService,
            JwtService jwtService,
            PaperRepository paperRepository,
            OrderRepository orderRepository,
            OrderPaperRepository orderPaperRepository,
            HttpServletRequest httpServletRequest,
            StudentPaperRepository studentPaperRepository, PaymentService paymentService,
            PrinterRepository printerRepository,
            PrintingLogRepository printingLogRepository,
            FileRepository fileRepository,
            FileTypeRepository fileTypeRepository
    )

     {
        this.userService = userService;
        this.jwtService = jwtService;
        this.paperRepository = paperRepository;
        this.orderRepository = orderRepository;
        this.orderPaperRepository = orderPaperRepository;
        this.httpServletRequest = httpServletRequest;
        this.studentPaperRepository = studentPaperRepository;
        this.paymentService = paymentService;
        this.printerRepository = printerRepository;
        this.printingLogRepository = printingLogRepository;
        this.fileRepository = fileRepository;
        this.fileTypeRepository = fileTypeRepository;
     }

    public CreatePaymentBuyResponse createPayment(BuyPageDTO buyPageDTO, HttpServletRequest request) {
        if (!paperRepository.existsByType(buyPageDTO.getPaperType())) {
            throw new RuntimeException("Paper type not found");
        }
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = userService.getUserbyUsername(jwtService.extractUsername(token)).getId();
        String paperType = buyPageDTO.getPaperType();
        Long quantity = buyPageDTO.getQuantity();
        Long totalPrice = buyPageDTO.getQuantity() * paperRepository.findByType(buyPageDTO.getPaperType()).getPrice();
        String paymentUrl = paymentService.createVnPayPayment(request, totalPrice, studentId, paperType, quantity);
        return new CreatePaymentBuyResponse(studentId, totalPrice, paymentUrl);
    }

    @Transactional

    public void buyPages(HttpServletRequest request) {
        if (paymentService.isTransactionProcessed(request.getParameter("vnp_TxnRef"))) {
            throw new RuntimeException("Transaction already processed");
        }
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        if (vnp_OrderInfo == null) {
            throw new RuntimeException("Order info not found");
        }
        String[] parts = vnp_OrderInfo.split("-");
        Long studentId = Long.parseLong(parts[0]); // Convert the first part to Long
        String paperType = parts[1]; // Second part as String
        Long quantity = Long.parseLong(parts[2]);

        Order order = new Order();
        order.setStudentId(studentId);
        order.setTotalPrice(paperRepository.findByType(paperType).getPrice() * quantity);
        orderRepository.save(order);

        OrderPaper orderPaper = new OrderPaper();
        orderPaper.setOrder(order);
        orderPaper.setPaperType(paperType);
        orderPaper.setQuantity(quantity);
        orderPaperRepository.save(orderPaper);

        StudentPaper studentPaper = studentPaperRepository.findByStudentIdAndPaperType(studentId, paperType);

        if (studentPaper == null) {
            studentPaper = new StudentPaper();
            studentPaper.setStudentId(studentId);
            studentPaper.setPaperType(paperType);
            studentPaper.setQuantity(quantity);
        } else {
            studentPaper.setQuantity(studentPaper.getQuantity() + quantity);
        }
        studentPaperRepository.save(studentPaper);
        paymentService.markTransactionAsProcessed(request.getParameter("vnp_TxnRef"));
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

    public List<PageResponse> getPagesLeft(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        List<StudentPaper> studentPapers = studentPaperRepository.findByStudentId(studentId);
        List<PageResponse> responses = new ArrayList<>();

        for (StudentPaper studentPaper : studentPapers) {
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

    public List<OrderHistoryResponse> getPageBuyingHistory(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        List<Order> orders = orderRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
        List<OrderHistoryResponse> responses = new ArrayList<>();

        for (Order order : orders) {
            OrderHistoryResponse response = new OrderHistoryResponse();
            response.setOrderId(order.getId());
            response.setTotalPrice(order.getTotalPrice());

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

    public PrintingLogResponse printDocument(PrintRequestDTO request, HttpServletRequest httpRequest) {
        // Get user id from token
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        Printer printer = printerRepository.findById(request.getPrinterId())
                .orElseThrow(() -> new RuntimeException("Printer not found"));

        Paper paper = paperRepository.findByType(request.getPaperType());

        File file = fileRepository.findById(request.getFileId())
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("File does not belong to the student");
        }

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

        printingLog = printingLogRepository.save(printingLog);

        studentPaper.setQuantity(studentPaper.getQuantity() - request.getNumPages());
        studentPaperRepository.save(studentPaper);

        return PrintingLogResponse.fromPrintingLog(printingLog);
    }


    public FileResponse createFile(FileDTO fileDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        User student = userService.findById(userId);

        if (!fileTypeRepository.existsByType(fileDTO.getFileType())) {
            throw new RuntimeException("Invalid file type");
        }

        File file = new File();
        file.setFileName(fileDTO.getFileName());
        file.setFileType(fileDTO.getFileType());
        file.setStudent(student);

        file = fileRepository.save(file);

        FileResponse response = new FileResponse();
        response.setFileId(file.getId());
        response.setFileName(file.getFileName());
        response.setFileType(file.getFileType());
        return response;
    }

    public List<PrintingLogResponse> getStudentPrintingLogs(HttpServletRequest httpRequest) {
        // Get user id from token
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        // Get student
        User student = userService.findById(studentId);

        // Get all printing logs for files owned by the student
        List<PrintingLog> printingLogs = printingLogRepository.findByFileStudentOrderByTimeDesc(student);

        return printingLogs.stream()
                .map(PrintingLogResponse::fromPrintingLog)
                .collect(Collectors.toList());
    }

    public List<PrintingLogResponse> getPrinterLogs(Long printerId, HttpServletRequest httpRequest) {
        // Get user id from token
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        // Verify printer exists
        if (!printerRepository.existsById(printerId)) {
            throw new RuntimeException("Printer not found");
        }

        List<PrintingLog> printingLogs = printingLogRepository.findByPrinterIdOrderByTimeDesc(printerId);

        return printingLogs.stream()
                .map(PrintingLogResponse::fromPrintingLog)
                .collect(Collectors.toList());
    }
    public List<FileResponse> getStudentFiles(HttpServletRequest httpRequest) {
        // Get user id from token
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Long studentId = jwtService.extractUserId(token);

        // Get student
        User student = userService.findById(studentId);

        List<File> files = fileRepository.findByStudentOrderByIdDesc(student);

        return files.stream()
                .map(FileResponse::fromFile)
                .collect(Collectors.toList());
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
