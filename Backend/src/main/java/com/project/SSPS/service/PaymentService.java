
package com.project.SSPS.service;

import com.project.SSPS.config.VNPAYConfig;
import com.project.SSPS.dto.PaymentDTO;
import com.project.SSPS.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;
    private final Map<String, Boolean> processedTransactions = new ConcurrentHashMap<>();

    public String createVnPayPayment(HttpServletRequest request, Long amount, Long studentId, String paperType,
            Long quantity) {
        amount = amount * 100;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_OrderType", "billpayment");
        vnpParamsMap.put("vnp_OrderInfo", studentId.toString() + "-" + paperType + "-" + quantity.toString());
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        // build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return paymentUrl;
    }

    public boolean isTransactionProcessed(String vnp_TxnRef) {
        return processedTransactions.containsKey(vnp_TxnRef);
    }

    public void markTransactionAsProcessed(String vnp_TxnRef) {
        processedTransactions.put(vnp_TxnRef, true);
    }
}
