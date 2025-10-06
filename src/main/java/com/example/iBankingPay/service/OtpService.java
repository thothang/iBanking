package com.example.iBankingPay.service;

import com.example.iBankingPay.entity.Otp;
import com.example.iBankingPay.exception.InvalidOtpException;
import com.example.iBankingPay.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class OtpService {
    private final OtpRepository otpRepository;

    @Value("${otp.expiry.minutes:5}")
    private int otpExpiryMinutes;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public Otp generateOtp(Long userId) {
        String code = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(otpExpiryMinutes);

        Otp otp = new Otp();
        otp.setUserId(userId);
        otp.setCode(code);
        otp.setExpiryTime(expiry);
        otp.setUsed(false);
        return otpRepository.save(otp);
    }

    public void validateOtp(Long userId, String code) {
        Otp otp = otpRepository.findByUserIdAndCodeAndUsedFalse(userId, code)
                .orElseThrow(() -> new InvalidOtpException("OTP not found or already used"));

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new InvalidOtpException("OTP has expired");
        }

        otp.setUsed(true);
        otpRepository.save(otp);
    }
}
