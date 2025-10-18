package com.fooddelivery.controller;

import com.fooddelivery.entity.Payment;
import com.fooddelivery.service.PaymentService;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController @RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService s){ this.service=s; }
    @PostMapping
    public Payment create(@RequestBody Payment p){ return service.create(p); }
    @GetMapping("/{id}")
    public Payment one(@PathVariable Long id){ return service.getById(id); }
    @GetMapping("/order/{orderId}")
    public Optional<Payment> byOrder(@PathVariable Long orderId){ return service.getByOrderId(orderId); }
}
