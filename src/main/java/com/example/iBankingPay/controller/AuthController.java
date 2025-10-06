package com.example.iBankingPay.controller;

import com.example.iBankingPay.dto.LoginRequest;
import com.example.iBankingPay.dto.LoginResponse;
import com.example.iBankingPay.service.AuthService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }
}
