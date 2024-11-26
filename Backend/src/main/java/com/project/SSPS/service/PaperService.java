package com.project.SSPS.service;

import com.project.SSPS.dto.*;
import com.project.SSPS.model.*;
import com.project.SSPS.repository.*;
import com.project.SSPS.response.BuyPageResponse;
import com.project.SSPS.response.CreatePaymentBuyResponse;
import com.project.SSPS.response.OrderHistoryResponse;
import com.project.SSPS.response.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    private final PaymentService paymentService;

    public PaperService(UserService userService,
            JwtService jwtService,
            PaperRepository paperRepository,
            OrderRepository orderRepository,
            OrderPaperRepository orderPaperRepository,
            HttpServletRequest httpServletRequest,
            StudentPaperRepository studentPaperRepository, PaymentService paymentService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.paperRepository = paperRepository;
        this.orderRepository = orderRepository;
        this.orderPaperRepository = orderPaperRepository;
        this.httpServletRequest = httpServletRequest;
        this.studentPaperRepository = studentPaperRepository;
        this.paymentService = paymentService;
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
    public void buyPages(Long studentId, String paperType, Long quantity) {

        Order order = new Order();
        order.setStudentId(studentId);
        order.setTotalPrice(paperRepository.findByType(paperType).getPrice() * quantity);
        orderRepository.save(order);

        OrderPaper orderPaper = new OrderPaper();
        orderPaper.setOrderId(order.getId());
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

    public List<PageResponse> getPagesLeft(Long studentId) {
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

    public List<OrderHistoryResponse> getPageBuyingHistory(Long studentId) {
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

    public String delete(Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }
        paperRepository.deleteById(id);
        return "Paper deleted successfully";
    }
}
