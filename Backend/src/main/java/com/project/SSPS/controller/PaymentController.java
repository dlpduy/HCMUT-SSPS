package com.project.SSPS.controller;

import com.project.SSPS.dto.PaymentDTO;
import com.project.SSPS.response.ResponseObject;
import com.project.SSPS.service.PaperService;
import com.project.SSPS.service.PaymentService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // @GetMapping("/vn-pay")
    // public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest
    // request) {
    // return new ResponseObject<>(HttpStatus.OK, "Success",
    // paymentService.createVnPayPayment(request));
    // }
    @GetMapping("/vn-pay-callback")
    @ApiMessage("Payment callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request) {
        try {
            String status = request.getParameter("vnp_ResponseCode");
            if (status.equals("00")) {
                paperService.buyPages(request);
                return new ResponseObject<>(HttpStatus.OK, "Success",
                        new PaymentDTO.VNPayResponse("00", "Buy page(s) successfully"));
            } else {
                return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
            }
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }
    }
}
