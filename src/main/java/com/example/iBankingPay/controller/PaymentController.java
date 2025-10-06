package com.example.iBankingPay.controller;

import com.example.iBankingPay.service.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173") // nếu frontend chạy Vite
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/start")
    @PreAuthorize("isAuthenticated()")
    public String startPayment(@RequestParam Long userId, @RequestParam String studentId) {
        paymentService.startPayment(userId, studentId);
        return "OTP sent to email.";
    }

    @PostMapping("/confirm")
    @PreAuthorize("isAuthenticated()")
    public String confirmPayment(@RequestParam Long userId, @RequestParam String studentId, @RequestParam String otp) {
        paymentService.confirmPayment(userId, studentId, otp);
        return "Payment successful.";
    }
}