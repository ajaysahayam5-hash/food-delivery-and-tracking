package com.fooddelivery.service;

import com.fooddelivery.entity.Payment;
import com.fooddelivery.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PaymentService abstraction.
 * Why: payment records must always carry a transaction id (Review-II business logic).
 */
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock PaymentRepository repo;
    PaymentService service;

    @BeforeEach
    void setUp() {
        service = new PaymentService(repo);
    }

    @Test
    void create_assignsTxnIdAndSaves() {
        when(repo.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        Payment p = Payment.builder().orderId(10L).paymentMethod("CASH")
                .amount(new BigDecimal("240.00")).build();
        Payment saved = service.create(p);

        assertNotNull(saved.getTransactionId());
        assertTrue(saved.getTransactionId().startsWith("TXN"));
    }

    @Test
    void getById_missing_throws() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(1L));
    }

    @Test
    void getByOrderId_delegatesToRepo() {
        when(repo.findByOrderId(10L)).thenReturn(Optional.empty());
        assertTrue(service.getByOrderId(10L).isEmpty());
        verify(repo).findByOrderId(10L);
    }
}
