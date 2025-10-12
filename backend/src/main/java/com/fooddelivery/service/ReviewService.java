package com.fooddelivery.service;

import com.fooddelivery.entity.Review;
import com.fooddelivery.repository.ReviewRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repo;
    private final UserRepository userRepo;

    public ReviewService(ReviewRepository r, UserRepository ur) {
        this.repo = r;
        this.userRepo = ur;
    }

    public Review create(Long customerId, Review rev) {
        rev.setCustomerId(customerId);
        return repo.save(rev);
    }

    public List<Review> byRestaurant(Long restaurantId) {
        return repo.findByRestaurantId(restaurantId);
    }

    public List<Review> byCustomer(Long customerId) {
        return repo.findByCustomerId(customerId);
    }

    public List<Review> all() {
        return repo.findAll();
    }
}