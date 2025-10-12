package com.fooddelivery.service;

import com.fooddelivery.dto.*;
import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import com.fooddelivery.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Authentication business logic: register, login, OTP generate/verify.
 * Why: keeps controllers thin; passwords BCrypt-hashed, OTP expiry enforced, JWT issued on success.
 */
@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepo;
    private final OtpTokenRepository otpRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    /**
     * Creates the service.
     * @param ur user repo
     * @param or otp repo
     * @param pe BCrypt encoder
     * @param ju JWT helper
     */
    public AuthService(UserRepository ur, OtpTokenRepository or, PasswordEncoder pe, JwtUtil ju){
        this.userRepo=ur; this.otpRepo=or; this.encoder=pe; this.jwtUtil=ju;
    }

    /**
     * Registers a new user, hashes password, creates OTP.
     * @param req registration DTO (validated)
     * @return auth response with JWT
     */
    public AuthResponse register(RegisterRequest req){
        if(userRepo.existsByEmail(req.getEmail())) throw new RuntimeException("Email already exists");
        User u = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(encoder.encode(req.getPassword()))
                .role(req.getRole()!=null? req.getRole().toUpperCase(): "CUSTOMER")
                .enabled(true)
                .emailVerified(false)
                .build();
        userRepo.save(u);
        // generate OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        OtpToken token = OtpToken.builder().email(u.getEmail()).otp(otp).expiresAt(LocalDateTime.now().plusMinutes(10)).verified(false).build();
        otpRepo.save(token);
        log.info("OTP generated for user signup: {}", u.getEmail());
        log.debug("OTP value (dev-mode only): {}", otp);
        String jwt = jwtUtil.generateToken(u.getEmail(), u.getRole());
        return AuthResponse.builder().token(jwt).id(u.getId()).email(u.getEmail()).fullName(u.getFullName()).role(u.getRole()).message("Registered. OTP sent (see console)").build();
    }

    /**
     * Authenticates by email + BCrypt password.
     * @param req login DTO
     * @return auth response with JWT
     */
    public AuthResponse login(AuthRequest req){
        User u = userRepo.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if(!encoder.matches(req.getPassword(), u.getPassword())) throw new RuntimeException("Invalid credentials");
        String jwt = jwtUtil.generateToken(u.getEmail(), u.getRole());
        return AuthResponse.builder().token(jwt).id(u.getId()).email(u.getEmail()).fullName(u.getFullName()).role(u.getRole()).message("Login successful").build();
    }

    /**
     * Generates a new 6-digit OTP for resend.
     * @param email user email
     * @return otp value (returned for dev-mode; never log plain password)
     */
    public String generateOtp(String email){
        User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String otp = String.format("%06d", new Random().nextInt(999999));
        OtpToken t = OtpToken.builder().email(email).otp(otp).expiresAt(LocalDateTime.now().plusMinutes(10)).verified(false).build();
        otpRepo.save(t);
        log.info("OTP resent for {}", email);
        return otp;
    }

    /**
     * Verifies OTP, marks email verified.
     * @param email user email
     * @param otp 6-digit code
     * @return success message
     */
    public String verifyOtp(String email, String otp){
        OtpToken t = otpRepo.findTopByEmailOrderByCreatedAtDesc(email).orElseThrow(() -> new RuntimeException("OTP not found"));
        if(t.getVerified()) return "Already verified";
        if(t.getExpiresAt().isBefore(LocalDateTime.now())) throw new RuntimeException("OTP expired");
        if(!t.getOtp().equals(otp)) throw new RuntimeException("Invalid OTP");
        t.setVerified(true);
        otpRepo.save(t);
        User u = userRepo.findByEmail(email).orElseThrow();
        u.setEmailVerified(true);
        userRepo.save(u);
        return "OTP verified successfully";
    }
}
