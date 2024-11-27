package com.project.SSPS.response;

import com.project.SSPS.model.Order;
import com.project.SSPS.model.OrderPaper;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentLogsResponse {
    private Long paymentLogsId;
    private Long studentId;
    private String paperType;
    private Long quantity;
    private Long totalPrice;
    private LocalDateTime paymentDateTime;
    private Long unitPrice;

    public static PaymentLogsResponse fromOrderPaper(OrderPaper orderPaper, Order order) {

        return PaymentLogsResponse.builder()
                .paymentLogsId(orderPaper.getId())
                .studentId(order.getStudentId())
                .paperType(orderPaper.getPaperType())
                .quantity(orderPaper.getQuantity())
                .totalPrice(order.getTotalPrice())
                .paymentDateTime(orderPaper.getCreatedAt())
                .unitPrice(order.getTotalPrice() / orderPaper.getQuantity())
                .build();
    }
}
