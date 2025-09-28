package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="favorites")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Favorite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long restaurantId;
    private Long menuItemId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
