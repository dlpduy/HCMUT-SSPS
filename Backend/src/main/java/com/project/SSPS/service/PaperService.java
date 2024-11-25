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

@Service
public class PaperService {
    @Autowired
    private StudentPaperRepository studentPaperRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPaperRepository orderPaperRepository;

    @Transactional
    public void buyPages(Long studentId, BuyPageDTO request) {
        Order order = new Order();
        order.setStudentId(studentId);
        order.setTime(LocalDateTime.now());
        order.setTotalPrice(calculatePrice(request.getPaperType(), request.getQuantity()));
        orderRepository.save(order);

        OrderPaper orderPaper = new OrderPaper();
        orderPaper.setOrderId(order.getId());
        orderPaper.setPaperType(request.getPaperType());
        orderPaper.setQuantity(request.getQuantity());
        orderPaperRepository.save(orderPaper);

        StudentPaper studentPaper = studentPaperRepository.findByStudentIdAndPaperType(studentId, request.getPaperType());

        if (studentPaper == null) {
           studentPaper = new StudentPaper();
           studentPaper.setStudentId(studentId);
           studentPaper.setPaperType(request.getPaperType());
           studentPaper.setQuantity(request.getQuantity());
        } else {
            studentPaper.setQuantity(studentPaper.getQuantity() + request.getQuantity());
        }
        studentPaperRepository.save(studentPaper);
    }

    public List<PageResponse> getPagesLeft(Long studentId) {
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
    private Double calculatePrice(String paperType, Long quantity) {
        final double A4_PRICE = 200.0;
        return switch (paperType.toUpperCase()) {
            case "A4" -> A4_PRICE * quantity;
            case "A3" -> (A4_PRICE * 2) * quantity;  // A3 costs twice as much as A4
            default -> throw new IllegalArgumentException("Invalid paper type. Must be either A3 or A4");
        };
    }
}
