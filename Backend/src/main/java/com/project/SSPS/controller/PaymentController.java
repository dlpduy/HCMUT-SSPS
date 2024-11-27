package com.project.SSPS.controller;

import com.project.SSPS.dto.PaymentDTO;
import com.project.SSPS.response.ResponseObject;
import com.project.SSPS.service.PaperService;
import com.project.SSPS.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor

// Thông tin tài khoản dùng để test nhé
// Ngân hàng NCB
// Số thẻ 9704198526191432198
// Tên chủ thẻ NGUYEN VAN A
// Ngày phát hành 07/15
// Mật khẩu OTP 123456
public class PaymentController {
    private final PaperService paperService;
    private final PaymentService paymentService;

    // @GetMapping("/vn-pay")
    // public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest
    // request) {
    // return new ResponseObject<>(HttpStatus.OK, "Success",
    // paymentService.createVnPayPayment(request));
    // }
    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        if (vnp_OrderInfo == null) {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
        String[] parts = vnp_OrderInfo.split("-");
        Long studentId = Long.parseLong(parts[0]); // Convert the first part to Long
        String paperType = parts[1]; // Second part as String
        Long quantity = Long.parseLong(parts[2]);

        if (paymentService.isTransactionProcessed(request.getParameter("vnp_TxnRef"))) {
            return new ResponseObject<>(HttpStatus.CONFLICT, "Transaction already processed", null);
        }

        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            paperService.buyPages(studentId, paperType, quantity);
            paymentService.markTransactionAsProcessed(request.getParameter("vnp_TxnRef"));
            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success"));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
}
