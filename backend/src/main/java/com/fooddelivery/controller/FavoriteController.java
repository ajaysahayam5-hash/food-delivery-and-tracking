package com.fooddelivery.controller;

import com.fooddelivery.entity.Favorite;
import com.fooddelivery.service.FavoriteService;
import com.fooddelivery.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService service;
    private final UserRepository userRepo;

    public FavoriteController(FavoriteService s, UserRepository ur) {
        this.service = s;
        this.userRepo = ur;
    }

    private Long uid(Authentication auth) {
        return userRepo.findByEmail(auth.getName()).orElseThrow().getId();
    }

    @GetMapping
    public List<Favorite> get(Authentication auth) {
        return service.getByUser(uid(auth));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, Long> body, Authentication auth) {
        Long userId = uid(auth);
        if (body.containsKey("restaurantId")) {
            return ResponseEntity.ok(service.addRestaurant(userId, body.get("restaurantId")));
        } else if (body.containsKey("menuItemId")) {
            return ResponseEntity.ok(service.addMenuItem(userId, body.get("menuItemId")));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "restaurantId or menuItemId required"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        service.remove(id);
        return ResponseEntity.ok(Map.of("message", "Removed"));
    }
}