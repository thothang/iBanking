package com.example.iBankingPay.controller;

import com.example.iBankingPay.entity.StudentFee;
import com.example.iBankingPay.service.StudentFeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173") // nếu frontend chạy Vite
@RestController
@RequestMapping("/api/student")
public class StudentFeeController {
    private final StudentFeeService studentFeeService;

    public StudentFeeController(StudentFeeService studentFeeService) {
        this.studentFeeService = studentFeeService;
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("isAuthenticated()")
    public StudentFee getStudentInfo(@PathVariable String studentId) {
        return studentFeeService.getStudentById(studentId);
    }
}