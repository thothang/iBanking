package com.example.iBankingPay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String fullName;

    @NotBlank
    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Email
    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank
    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;
}
