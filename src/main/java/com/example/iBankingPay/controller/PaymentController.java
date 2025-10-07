package com.example.iBankingPay.controller;

import com.example.iBankingPay.dto.ApiResponse;
import com.example.iBankingPay.dto.PaymentRequest;
import com.example.iBankingPay.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/start")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> startPayment(@RequestBody PaymentRequest request) {
        paymentService.startPayment(request.getUserId(), request.getStudentId());
        return ResponseEntity.ok(new ApiResponse("OTP sent to email."));
    }

    @PostMapping("/confirm")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentRequest request) {
        var response = paymentService.confirmPayment(request.getUserId(), request.getStudentId(), request.getOtp());
        return ResponseEntity.ok(response);
    }

}
