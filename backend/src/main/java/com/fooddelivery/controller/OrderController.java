package com.fooddelivery.controller;

import com.fooddelivery.entity.Order;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Order endpoints: place/cancel/list/update-status across the PLACED→DELIVERED lifecycle.
 * Why: thin controller — pricing, payment and delivery creation live in OrderService.
 */
@RestController @RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final UserRepository userRepo;
    public OrderController(OrderService s, UserRepository ur){ this.service=s; this.userRepo=ur; }
    private Long uid(Authentication a){ return userRepo.findByEmail(a.getName()).orElseThrow().getId(); }

    @PostMapping
    public Order place(@RequestBody Map<String,Object> body, Authentication auth){
        Long rid = body.get("restaurantId")!=null? Long.valueOf(body.get("restaurantId").toString()): null;
        String addr = (String) body.get("deliveryAddress");
        Long addrId = body.get("deliveryAddressId")!=null? Long.valueOf(body.get("deliveryAddressId").toString()): null;
        String pm = body.get("paymentMethod")!=null? body.get("paymentMethod").toString(): "CASH";
        return service.placeOrder(uid(auth), rid, addr, addrId, pm);
    }

    @GetMapping
    public List<Order> my(Authentication auth){ return service.myOrders(uid(auth)); }

    @GetMapping("/all")
    public List<Order> all(){ return service.allOrders(); }

    @GetMapping("/{id}")
    public Order one(@PathVariable Long id){ return service.getById(id); }

    @PutMapping("/{id}/status")
    public Order status(@PathVariable Long id, @RequestBody Map<String,String> body){ return service.updateStatus(id, body.get("status")); }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, Authentication auth){
        service.cancel(id, uid(auth));
        return ResponseEntity.ok(Map.of("message","Cancelled"));
    }

    @GetMapping("/restaurant/{rid}")
    public List<Order> byRestaurant(@PathVariable Long rid){ return service.restaurantOrders(rid); }
}
