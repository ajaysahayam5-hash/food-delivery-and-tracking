package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private BigDecimal totalAmount;
    private BigDecimal deliveryFee = new BigDecimal("40.00");
    private BigDecimal taxAmount = BigDecimal.ZERO;
    private String orderStatus = "PLACED"; // PLACED, CONFIRMED, PREPARING, READY_FOR_PICKUP, PICKED_UP, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private String paymentStatus = "Pending";
    private String paymentMethod = "CASH";
    @Column(length=1000) private String deliveryAddress;
    private Long deliveryAddressId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist void prePersist(){ createdAt = LocalDateTime.now(); updatedAt=LocalDateTime.now(); }
    @PreUpdate void preUpdate(){ updatedAt=LocalDateTime.now(); }
}
