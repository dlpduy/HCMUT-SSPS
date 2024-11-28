package com.project.SSPS.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentLogsListResponse {
    List<PaymentLogsResponse> paymentLogsResponseList;
    int totalPages;
}
