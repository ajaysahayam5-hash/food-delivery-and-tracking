package com.fooddelivery.controller;

import com.fooddelivery.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final RestaurantRepository restaurantRepo;
    private final DeliveryPartnerRepository partnerRepo;
    private final PaymentRepository paymentRepo;

    public AdminController(UserRepository u, OrderRepository o, RestaurantRepository r, DeliveryPartnerRepository dp, PaymentRepository p){
        this.userRepo=u; this.orderRepo=o; this.restaurantRepo=r; this.partnerRepo=dp; this.paymentRepo=p;
    }

    @GetMapping("/stats")
    public Map<String,Object> stats(){
        return Map.of(
            "customers", userRepo.findAll().stream().filter(u->"CUSTOMER".equals(u.getRole())).count(),
            "restaurants", restaurantRepo.count(),
            "orders", orderRepo.count(),
            "deliveryPartners", partnerRepo.count(),
            "payments", paymentRepo.count()
        );
    }
}
