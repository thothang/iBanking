    package com.example.iBankingPay.entity;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.*;

    import java.math.BigDecimal;

    @Entity
    @Table(name = "student_fee")
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public class StudentFee {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Column(nullable = false, length = 20, unique = true)
        private String studentId;

        @NotBlank
        @Column(nullable = false, length = 100)
        private String studentName;

        @Column(nullable = false, precision = 18, scale = 2)
        private BigDecimal amount;

        @Column(nullable = false)
        private boolean paid = false;
    }

