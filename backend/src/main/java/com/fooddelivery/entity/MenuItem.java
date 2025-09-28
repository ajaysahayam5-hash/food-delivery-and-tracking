package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="menu_items")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long restaurantId;
    private Long categoryId;
    @Column(nullable=false) private String foodName;
    @Column(length=1000) private String description;
    @Column(nullable=false) private BigDecimal price;
    private String imageUrl;
    private Boolean isVeg = true;
    private Boolean availability = true;
    private Double rating = 4.2;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
