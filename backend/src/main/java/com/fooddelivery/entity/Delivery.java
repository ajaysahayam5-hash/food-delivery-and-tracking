package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="deliveries")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true) private Long orderId;
    private Long deliveryPartnerId;
    private String trackingStatus = "Assigned";
    private String estimatedTime = "30 min";
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
