package com.project.SSPS.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderHistoryResponse {
    private Long orderId;
    private Double totalPrice;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<OrderPaperDetail> papers;

    @Data
    public static class OrderPaperDetail {
        private String paperType;
        private Long quantity;
    }
}
