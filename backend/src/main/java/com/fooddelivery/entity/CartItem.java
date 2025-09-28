package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="cart_items", uniqueConstraints=@UniqueConstraint(columnNames={"cartId","menuItemId"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cartId;
    private Long menuItemId;
    private Integer quantity = 1;
    private BigDecimal price;
    private LocalDateTime addedAt = LocalDateTime.now();
}
