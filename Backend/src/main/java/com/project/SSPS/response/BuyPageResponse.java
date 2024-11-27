package com.project.SSPS.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyPageResponse {
    private Long studentId;
    private String paperType;
    private Long quantity;
    private Long totalPrice;
}
