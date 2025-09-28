package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="delivery_locations")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DeliveryLocation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long deliveryId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String currentLocation;
    private LocalDateTime updatedAt = LocalDateTime.now();
}
