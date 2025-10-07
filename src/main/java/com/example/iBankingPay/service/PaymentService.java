package com.example.iBankingPay.service;

import com.example.iBankingPay.dto.PaymentResponse;
import com.example.iBankingPay.entity.Otp;
import com.example.iBankingPay.entity.Payment;
import com.example.iBankingPay.entity.StudentFee;
import com.example.iBankingPay.entity.User;
import com.example.iBankingPay.exception.InsufficientBalanceException;
import com.example.iBankingPay.exception.NotFoundException;
import com.example.iBankingPay.repository.PaymentRepository;
import com.example.iBankingPay.repository.StudentFeeRepository;
import com.example.iBankingPay.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final StudentFeeRepository studentFeeRepository;
    private final OtpService otpService;
    private final MailService mailService;

    public PaymentService(PaymentRepository paymentRepository,
                          UserRepository userRepository,
                          StudentFeeRepository studentFeeRepository,
                          OtpService otpService,
                          MailService mailService) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.studentFeeRepository = studentFeeRepository;
        this.otpService = otpService;
        this.mailService = mailService;
    }

    public void startPayment(Long userId, String studentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        StudentFee fee = studentFeeRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("Student fee not found for studentId: " + studentId));

        if (fee.isPaid()) {
            throw new RuntimeException("Fee already paid for studentId: " + studentId);
        }

        if (user.getBalance().compareTo(fee.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to pay fee");
        }

        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setStudentId(studentId);
        payment.setAmount(fee.getAmount());
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        Otp otp = otpService.generateOtp(userId);
        mailService.sendEmail(user.getEmail(), "Your OTP Code", "OTP: " + otp.getCode());
    }

    public PaymentResponse confirmPayment(Long userId, String studentId, String otpCode) {
        otpService.validateOtp(userId, otpCode);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        StudentFee fee = studentFeeRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("Student fee not found for studentId: " + studentId));

        user.setBalance(user.getBalance().subtract(fee.getAmount()));
        userRepository.save(user);

        fee.setPaid(true);
        fee.setAmount(BigDecimal.ZERO);
        studentFeeRepository.save(fee);

        Payment payment = paymentRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new NotFoundException("Payment record not found for userId: " + userId));

        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

        mailService.sendEmail(
                user.getEmail(),
                "Payment Success",
                "Payment for " + fee.getStudentName() + " successful."
        );

        return new PaymentResponse(
                "Payment successful.",
                user.getBalance(),
                user,
                fee,
                payment
        );
    }

}
