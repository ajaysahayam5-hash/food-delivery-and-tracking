package com.fooddelivery.service;

import com.fooddelivery.entity.User;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService profile logic.
 * Why: user module is a Review-II major module; covers lookup, update and password hashing branches.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepo;
    @Mock PasswordEncoder encoder;

    UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(userRepo, encoder);
    }

    @Test
    void getById_found_returnsUser() {
        User u = User.builder().id(1L).email("a@test.com").fullName("A").role("CUSTOMER").build();
        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        assertEquals("a@test.com", service.getById(1L).getEmail());
    }

    @Test
    void getById_missing_throws() {
        when(userRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(9L));
    }

    @Test
    void getByEmail_found_returnsUser() {
        User u = User.builder().id(2L).email("b@test.com").role("CUSTOMER").build();
        when(userRepo.findByEmail("b@test.com")).thenReturn(Optional.of(u));
        assertEquals(2L, service.getByEmail("b@test.com").getId());
    }

    @Test
    void update_withNewPassword_hashesAndSaves() {
        User existing = User.builder().id(3L).fullName("Old").phone("111").password("old-hash").role("CUSTOMER").build();
        when(userRepo.findById(3L)).thenReturn(Optional.of(existing));
        when(encoder.encode("newpass")).thenReturn("new-hash");
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User patch = User.builder().fullName("New").phone("222").password("newpass").build();
        User updated = service.update(3L, patch);

        assertEquals("New", updated.getFullName());
        assertEquals("new-hash", updated.getPassword());
        verify(encoder).encode("newpass");
    }

    @Test
    void update_withoutPassword_keepsOldHash() {
        User existing = User.builder().id(4L).fullName("Old").password("keep-me").role("CUSTOMER").build();
        when(userRepo.findById(4L)).thenReturn(Optional.of(existing));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User patch = User.builder().fullName("New2").phone("333").build();
        User updated = service.update(4L, patch);

        assertEquals("keep-me", updated.getPassword());
        verify(encoder, never()).encode(anyString());
    }

    @Test
    void getAll_delegatesToRepo() {
        when(userRepo.findAll()).thenReturn(List.of());
        assertNotNull(service.getAll());
        verify(userRepo).findAll();
    }
}
