package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity @Table(name="restaurants")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    @Column(nullable=false) private String restaurantName;
    @Column(length=1000) private String description;
    private String address;
    private String city;
    private String pincode;
    private String phone;
    private String email;
    private String imageUrl;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String status = "Open";
    private Double rating = 0.0;
    private String deliveryTime = "30-40 min";
    private Integer priceForTwo = 300;
    private Boolean isVeg = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
