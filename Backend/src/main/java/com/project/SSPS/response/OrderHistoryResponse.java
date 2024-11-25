package com.project.SSPS.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderHistoryResponse {
    private Long orderId;
    private Double totalPrice;
    private LocalDateTime time;
    private List<OrderPaperDetail> papers;

    @Data
    public static class OrderPaperDetail {
        private String paperType;
        private Long quantity;
    }
}
