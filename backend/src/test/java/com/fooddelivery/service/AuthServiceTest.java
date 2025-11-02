package com.fooddelivery.service;

import com.fooddelivery.dto.AuthRequest;
import com.fooddelivery.dto.AuthResponse;
import com.fooddelivery.dto.RegisterRequest;
import com.fooddelivery.entity.OtpToken;
import com.fooddelivery.entity.User;
import com.fooddelivery.repository.OtpTokenRepository;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService business logic.
 * Why: auth is a Review-II major module; covers register/login/OTP branches.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UserRepository userRepo;
    @Mock OtpTokenRepository otpRepo;
    @Mock PasswordEncoder encoder;
    @Mock JwtUtil jwtUtil;

    AuthService service;

    @BeforeEach
    void setUp() {
        service = new AuthService(userRepo, otpRepo, encoder, jwtUtil);
    }

    @Test
    void register_success_hashesPasswordAndIssuesJwt() {
        RegisterRequest req = new RegisterRequest("Ajay", "ajay@test.com", "999", "password123", "CUSTOMER");
        when(userRepo.existsByEmail("ajay@test.com")).thenReturn(false);
        when(encoder.encode("password123")).thenReturn("hashed");
        when(userRepo.save(any(User.class))).thenAnswer(i -> { User u = i.getArgument(0); u.setId(1L); return u; });
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("jwt-token");

        AuthResponse res = service.register(req);

        assertEquals("jwt-token", res.getToken());
        assertEquals("ajay@test.com", res.getEmail());
        verify(encoder).encode("password123");
        verify(otpRepo).save(any(OtpToken.class));
    }

    @Test
    void register_duplicateEmail_throws() {
        RegisterRequest req = new RegisterRequest("A", "dup@test.com", null, "password123", "CUSTOMER");
        when(userRepo.existsByEmail("dup@test.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> service.register(req));
    }

    @Test
    void login_success_returnsJwt() {
        User u = User.builder().id(2L).email("u@test.com").password("hashed").fullName("U").role("CUSTOMER").build();
        when(userRepo.findByEmail("u@test.com")).thenReturn(Optional.of(u));
        when(encoder.matches("plain", "hashed")).thenReturn(true);
        when(jwtUtil.generateToken("u@test.com", "CUSTOMER")).thenReturn("jwt");

        AuthResponse res = service.login(new AuthRequest("u@test.com", "plain"));
        assertEquals("jwt", res.getToken());
    }

    @Test
    void login_badPassword_throws() {
        User u = User.builder().email("u@test.com").password("hashed").role("CUSTOMER").build();
        when(userRepo.findByEmail("u@test.com")).thenReturn(Optional.of(u));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.login(new AuthRequest("u@test.com", "wrong")));
    }

    @Test
    void verifyOtp_success_marksVerified() {
        OtpToken t = OtpToken.builder().email("e@test.com").otp("123456").expiresAt(LocalDateTime.now().plusMinutes(5)).verified(false).build();
        User u = User.builder().id(5L).email("e@test.com").role("CUSTOMER").build();
        when(otpRepo.findTopByEmailOrderByCreatedAtDesc("e@test.com")).thenReturn(Optional.of(t));
        when(userRepo.findByEmail("e@test.com")).thenReturn(Optional.of(u));

        String msg = service.verifyOtp("e@test.com", "123456");
        assertEquals("OTP verified successfully", msg);
        assertTrue(t.getVerified());
    }

    @Test
    void verifyOtp_expired_throws() {
        OtpToken t = OtpToken.builder().email("e@test.com").otp("1").expiresAt(LocalDateTime.now().minusMinutes(1)).verified(false).build();
        when(otpRepo.findTopByEmailOrderByCreatedAtDesc("e@test.com")).thenReturn(Optional.of(t));
        assertThrows(RuntimeException.class, () -> service.verifyOtp("e@test.com", "1"));
    }

    @Test
    void verifyOtp_wrongCode_throws() {
        OtpToken t = OtpToken.builder().email("e@test.com").otp("111111").expiresAt(LocalDateTime.now().plusMinutes(5)).verified(false).build();
        when(otpRepo.findTopByEmailOrderByCreatedAtDesc("e@test.com")).thenReturn(Optional.of(t));
        assertThrows(RuntimeException.class, () -> service.verifyOtp("e@test.com", "999999"));
    }
}
