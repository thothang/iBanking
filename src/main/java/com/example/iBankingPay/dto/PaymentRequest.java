package com.example.iBankingPay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long userId;
    private String studentId;
    private String otp;

}
