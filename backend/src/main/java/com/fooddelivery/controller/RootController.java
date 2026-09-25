package com.fooddelivery.controller;

import com.fooddelivery.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Public landing endpoint for the API root.
 * Why: opening the backend URL in a browser should explain the API with links
 * instead of returning an access-denied page.
 */
@RestController
public class RootController {

    /**
     * Returns API welcome info with links to health, docs and frontend.
     * @return 200 OK with service links
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<Map<String, String>>> root() {
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
                "service", "food-delivery-backend",
                "health", "/api/health",
                "swagger", "/swagger-ui.html",
                "apiDocs", "/v3/api-docs"
        ), "Food Delivery API is running - see /swagger-ui.html for endpoints"));
    }
}
