package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="otp_tokens")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OtpToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String otp;
    private LocalDateTime expiresAt;
    private Boolean verified = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
