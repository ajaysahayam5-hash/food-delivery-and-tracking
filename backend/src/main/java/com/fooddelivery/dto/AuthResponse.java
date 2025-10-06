package com.fooddelivery.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private Long id;
    private String email;
    private String fullName;
    private String role;
    private String message;
}
