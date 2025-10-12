package com.fooddelivery.service;

import com.fooddelivery.entity.Payment;
import com.fooddelivery.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository repo;

    public PaymentService(PaymentRepository r) {
        this.repo = r;
    }

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