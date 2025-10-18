package com.fooddelivery.controller;

import com.fooddelivery.entity.Review;
import com.fooddelivery.service.ReviewService;
import com.fooddelivery.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService service;
    private final UserRepository userRepo;
    public ReviewController(ReviewService s, UserRepository ur){ this.service=s; this.userRepo=ur; }
    @PostMapping
    public Review create(@RequestBody Review rev, Authentication auth){
        Long customerId = userRepo.findByEmail(auth.getName()).orElseThrow().getId();
        return service.create(customerId, rev);
    }
    @GetMapping("/restaurant/{id}")
    public List<Review> byRestaurant(@PathVariable Long id){ return service.byRestaurant(id); }
    @GetMapping
    public List<Review> all(){ return service.all(); }
}
