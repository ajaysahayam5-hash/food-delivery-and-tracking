package com.fooddelivery.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    @NotBlank private String fullName;
    @Email @NotBlank private String email;
    private String phone;
    @NotBlank @Size(min=6) private String password;
    private String role = "CUSTOMER"; // CUSTOMER, RESTAURANT, DELIVERY_PARTNER, ADMIN
}
