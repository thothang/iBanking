package com.example.iBankingPay.service;

import com.example.iBankingPay.dto.LoginResponse;
import com.example.iBankingPay.entity.User;
import com.example.iBankingPay.exception.AuthenticationException;
import com.example.iBankingPay.repository.UserRepository;
import com.example.iBankingPay.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));
        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token, user);
    }
}