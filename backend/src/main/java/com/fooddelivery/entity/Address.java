package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="addresses")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String label = "Home";
    @Column(length=1000, nullable=false) private String fullAddress;
    private String city;
    private String pincode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isDefault = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
