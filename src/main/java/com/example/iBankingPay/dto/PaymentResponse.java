package com.example.iBankingPay.dto;

import com.example.iBankingPay.entity.Payment;
import com.example.iBankingPay.entity.StudentFee;
import com.example.iBankingPay.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponse {
    private String message;
    private BigDecimal remainingBalance;
    private User user;
    private StudentFee fee;
    private Payment payment;
}
