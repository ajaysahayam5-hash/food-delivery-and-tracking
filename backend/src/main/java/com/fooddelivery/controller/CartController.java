package com.fooddelivery.controller;

import com.fooddelivery.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.fooddelivery.repository.UserRepository;
import java.util.Map;

/**
 * Cart endpoints: get/add/update/remove/clear for the authenticated customer.
 * Why: user id comes from JWT — clients never pass userId directly.
 */
@RestController @RequestMapping("/api/cart")
public class CartController {
    private final CartService service;
    private final UserRepository userRepo;
    public CartController(CartService s, UserRepository ur){ this.service=s; this.userRepo=ur; }

    private Long uid(Authentication auth){
        return userRepo.findByEmail(auth.getName()).orElseThrow().getId();
    }

    @GetMapping
    public Object get(Authentication auth){ return service.getCart(uid(auth)); }

    @PostMapping("/items")
    public Object add(@RequestBody Map<String,Object> body, Authentication auth){
        Long mid = Long.valueOf(body.get("menuItemId").toString());
        Integer qty = body.get("quantity")!=null? Integer.valueOf(body.get("quantity").toString()):1;
        return service.addItem(uid(auth), mid, qty);
    }

    @PutMapping("/items/{id}")
    public Object update(@PathVariable Long id, @RequestBody Map<String,Object> body, Authentication auth){
        Integer qty = Integer.valueOf(body.get("quantity").toString());
        return service.updateQty(uid(auth), id, qty);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id, Authentication auth){
        service.removeItem(uid(auth), id);
        return ResponseEntity.ok(Map.of("message","Removed"));
    }

    @DeleteMapping
    public ResponseEntity<?> clear(Authentication auth){ service.clear(uid(auth)); return ResponseEntity.ok(Map.of("message","Cleared")); }
}
