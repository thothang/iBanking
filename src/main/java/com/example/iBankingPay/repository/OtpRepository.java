package com.example.iBankingPay.repository;

import com.example.iBankingPay.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByUserIdAndCodeAndUsedFalse(Long userId, String code);

}
