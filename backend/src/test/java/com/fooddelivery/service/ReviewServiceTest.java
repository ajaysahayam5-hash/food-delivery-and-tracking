package com.fooddelivery.service;

import com.fooddelivery.entity.Review;
import com.fooddelivery.repository.ReviewRepository;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReviewService.
 * Why: reviews attach to customer id server-side, never trusting client (Review-II security).
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock ReviewRepository repo;
    @Mock UserRepository userRepo;
    ReviewService service;

    @BeforeEach
    void setUp() {
        service = new ReviewService(repo, userRepo);
    }

    @Test
    void create_setsCustomerId() {
        when(repo.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        Review r = Review.builder().restaurantId(2L).rating(5).comments("Great!").build();
        Review saved = service.create(11L, r);

        assertEquals(11L, saved.getCustomerId());
        assertEquals(5, saved.getRating());
    }

    @Test
    void byRestaurant_delegatesToRepo() {
        when(repo.findByRestaurantId(2L)).thenReturn(List.of());
        assertNotNull(service.byRestaurant(2L));
        verify(repo).findByRestaurantId(2L);
    }

    @Test
    void byCustomer_delegatesToRepo() {
        when(repo.findByCustomerId(11L)).thenReturn(List.of());
        assertNotNull(service.byCustomer(11L));
        verify(repo).findByCustomerId(11L);
    }
}
