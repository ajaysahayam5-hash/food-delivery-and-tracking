package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="payments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true) private Long orderId;
    private String paymentMethod;
    private BigDecimal amount;
    @Column(unique=true) private String transactionId;
    private String paymentStatus = "Pending";
    private LocalDateTime paymentDate = LocalDateTime.now();
}
