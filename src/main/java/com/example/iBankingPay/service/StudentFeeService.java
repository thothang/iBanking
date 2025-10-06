package com.example.iBankingPay.service;

import com.example.iBankingPay.entity.StudentFee;
import com.example.iBankingPay.exception.ResourceNotFoundException;
import com.example.iBankingPay.repository.StudentFeeRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentFeeService {
    private final StudentFeeRepository studentFeeRepository;

    public StudentFeeService(StudentFeeRepository studentFeeRepository) {
        this.studentFeeRepository = studentFeeRepository;
    }

    public StudentFee getStudentById(String studentId) {
        return studentFeeRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
    }
}
