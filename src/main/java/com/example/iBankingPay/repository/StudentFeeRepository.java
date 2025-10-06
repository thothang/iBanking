package com.example.iBankingPay.repository;

import com.example.iBankingPay.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {

    Optional<StudentFee> findByStudentId(String studentId);

}
