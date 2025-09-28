package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="delivery_partners")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DeliveryPartner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String fullName;
    @Column(unique=true) private String phone;
    @Column(unique=true) private String email;
    private String vehicleNumber;
    private String vehicleType = "Bike";
    private Boolean availability = true;
    private Double rating = 4.5;
    private BigDecimal currentLatitude;
    private BigDecimal currentLongitude;
    private LocalDateTime createdAt = LocalDateTime.now();
}
