package com.fooddelivery.service;

import com.fooddelivery.entity.Review;
import com.fooddelivery.repository.ReviewRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Review logic: customer-scoped creation and restaurant/customer lookups.
 * Why: customer id is set server-side from JWT, never trusted from client.
 */
@Service
public class ReviewService {
    private final ReviewRepository repo;
    private final UserRepository userRepo;

    /**
     * Creates the service.
     * @param r review repository
     * @param ur user repository
     */
    public ReviewService(ReviewRepository r, UserRepository ur) {
        this.repo = r;
        this.userRepo = ur;
    }

    /**
     * Creates a review for the authenticated customer.
     * @param customerId author id from JWT
     * @param rev review body
     * @return saved review
     */
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