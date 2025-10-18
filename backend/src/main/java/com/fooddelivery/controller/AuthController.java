package com.fooddelivery.controller;

import com.fooddelivery.dto.*;
import com.fooddelivery.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService s){ this.service=s; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req){
        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req){
        return ResponseEntity.ok(service.login(req));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verify(@RequestBody Map<String,String> body){
        String email=body.get("email"), otp=body.get("otp");
        return ResponseEntity.ok(Map.of("message", service.verifyOtp(email, otp)));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resend(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String otp=service.generateOtp(email);
        return ResponseEntity.ok(Map.of("message","OTP resent (see console)","otp_dev", otp));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok(Map.of("message","Logged out (client should discard token)"));
    }
}
