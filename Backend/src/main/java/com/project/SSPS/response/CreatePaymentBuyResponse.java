package com.project.SSPS.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentBuyResponse {
    private Long studentId;
    private Long totalPrice;
    private String paymentUrl;
}
