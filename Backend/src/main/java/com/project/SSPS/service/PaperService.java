package com.project.SSPS.service;

import com.project.SSPS.dto.*;
import com.project.SSPS.model.*;
import com.project.SSPS.repository.*;
import com.project.SSPS.response.OrderHistoryResponse;
import com.project.SSPS.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.SSPS.dto.PaperDTO;
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
    private final  OrderPaperRepository orderPaperRepository;

    public PaperService(UserService userService,
                        JwtService jwtService,
                        PaperRepository paperRepository,
                        OrderRepository orderRepository,
                        OrderPaperRepository orderPaperRepository
                        ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.paperRepository = paperRepository;
        this.orderRepository = orderRepository;
        this.orderPaperRepository = orderPaperRepository;
    }
    @Transactional
    public void buyPages(Long studentId, BuyPageDTO request) {
        Order order = new Order();
        order.setStudentId(studentId);
        order.setTime(LocalDateTime.now());
        order.setTotalPrice(calculatePrice(request.getPaperType(), request.getQuantity()));
        orderRepository.save(order);

    public PaperResponse create(PaperDTO paperDTO, HttpServletRequest request) {
        Paper paper = new Paper();
        if (paperRepository.existsByType(paperDTO.getType())) {
            throw new RuntimeException("Paper already exists");
        }
        OrderPaper orderPaper = new OrderPaper();
        orderPaper.setOrderId(order.getId());
        orderPaper.setPaperType(request.getPaperType());
        orderPaper.setQuantity(request.getQuantity());
        orderPaperRepository.save(orderPaper);

        StudentPaper studentPaper = studentPaperRepository.findByStudentIdAndPaperType(studentId, request.getPaperType());
        paper.setType(paperDTO.getType());
        paper.setWidth(paperDTO.getWidth());
        paper.setHeight(paperDTO.getHeight());
        paper.setPrice(paperDTO.getPrice());
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        paper.setUser(userService.getUserbyUsername(jwtService.extractUsername(token)));
        paperRepository.save(paper);
        return PaperResponse.fromPaper(paper);
    }

        if (studentPaper == null) {
           studentPaper = new StudentPaper();
           studentPaper.setStudentId(studentId);
           studentPaper.setPaperType(request.getPaperType());
           studentPaper.setQuantity(request.getQuantity());
        } else {
            studentPaper.setQuantity(studentPaper.getQuantity() + request.getQuantity());
    public PaperResponse update(PaperDTO paperDTO, Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }
        studentPaperRepository.save(studentPaper);
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

        for(StudentPaper studentPaper : studentPapers) {
            PageResponse pageResponse = new PageResponse();
            pageResponse.setPaperType(studentPaper.getPaperType());
            pageResponse.setQuantity(studentPaper.getQuantity());
            responses.add(pageResponse);
    public PaperResponse getById(Long id) throws Exception {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new Exception("Paper not found");
        }
        return responses;
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
    public List<PaperResponse> getAll() {
        return paperRepository.findAll().stream().map(PaperResponse::fromPaper).toList();
    }

        return responses;
    }
    private Double calculatePrice(String paperType, Long quantity) {
        final double A4_PRICE = 200.0;
        return switch (paperType.toUpperCase()) {
            case "A4" -> A4_PRICE * quantity;
            case "A3" -> (A4_PRICE * 2) * quantity;  // A3 costs twice as much as A4
            default -> throw new IllegalArgumentException("Invalid paper type. Must be either A3 or A4");
        };
    public String delete(Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }
        paperRepository.deleteById(id);
        return "Paper deleted successfully";
    }
}
