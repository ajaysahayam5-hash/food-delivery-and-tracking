package com.fooddelivery.service;

import com.fooddelivery.entity.Address;
import com.fooddelivery.repository.AddressRepository;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AddressService ownership guards.
 * Why: addresses contain PII; unauthorized update must fail (Review-II security).
 */
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock AddressRepository repo;
    @Mock UserRepository userRepo;
    AddressService service;

    @BeforeEach
    void setUp() {
        service = new AddressService(repo, userRepo);
    }

    @Test
    void create_setsUserIdAndSaves() {
        when(repo.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address a = Address.builder().fullAddress("123 Main St").isDefault(false).build();
        Address saved = service.create(1L, a);

        assertEquals(1L, saved.getUserId());
        assertEquals("123 Main St", saved.getFullAddress());
    }

    @Test
    void update_wrongOwner_throws() {
        Address existing = Address.builder().id(5L).userId(1L).fullAddress("Old").build();
        when(repo.findById(5L)).thenReturn(Optional.of(existing));

        Address patch = Address.builder().fullAddress("New").build();
        assertThrows(RuntimeException.class, () -> service.update(2L, 5L, patch));
    }

    @Test
    void update_owner_ok() {
        Address existing = Address.builder().id(6L).userId(1L).fullAddress("Old").isDefault(false).build();
        when(repo.findById(6L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address patch = Address.builder().label("Home").fullAddress("New Addr")
                .city("Chennai").pincode("600001").isDefault(false).build();
        Address updated = service.update(1L, 6L, patch);

        assertEquals("New Addr", updated.getFullAddress());
        assertEquals("Chennai", updated.getCity());
    }

    @Test
    void delete_delegatesToRepo() {
        service.delete(7L);
        verify(repo).deleteById(7L);
    }
}
