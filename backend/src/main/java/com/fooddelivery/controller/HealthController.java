package com.fooddelivery.controller;

import com.fooddelivery.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Basic monitoring endpoint required by Review-II.
 * Why: hosting platforms and examiners need a no-auth liveness check.
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    /**
     * Returns service liveness.
     * @return 200 OK with {success:true, data:"OK"}
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        return ResponseEntity.ok(ApiResponse.ok(Map.of("status", "OK", "service", "food-delivery-backend"), "Service is running"));
    }
}
