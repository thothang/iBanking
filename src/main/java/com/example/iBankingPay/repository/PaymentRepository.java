package com.example.iBankingPay.repository;

import com.example.iBankingPay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findTopByUserIdOrderByCreatedAtDesc(Long userId);

}
