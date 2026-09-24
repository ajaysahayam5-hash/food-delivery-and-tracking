package com.fooddelivery.service;

import com.fooddelivery.entity.Payment;
import com.fooddelivery.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Payment record logic: create with transaction id, lookup by order.
 * Why: CASH/UPI/Card are recorded abstractions; TXN id is always generated server-side.
 */
@Service
public class PaymentService {
    private final PaymentRepository repo;

    /**
     * Creates the service.
     * @param r payment repository
     */
    public PaymentService(PaymentRepository r) {
        this.repo = r;
    }

    /**
     * Creates a payment with generated transaction id.
     * @param p payment to save
     * @return saved payment
     */
    public Payment create(Payment p) {
        p.setTransactionId("TXN" + System.currentTimeMillis());
        return repo.save(p);
    }

    public Optional<Payment> getByOrderId(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    public Payment getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}