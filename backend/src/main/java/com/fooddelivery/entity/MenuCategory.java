package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="menu_categories")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt = LocalDateTime.now();
}
