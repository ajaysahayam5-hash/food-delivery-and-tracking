package com.fooddelivery.service;

import com.fooddelivery.entity.Favorite;
import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.FavoriteRepository;
import com.fooddelivery.repository.MenuItemRepository;
import com.fooddelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for FavoriteService.
 * Why: favorites must validate target exists before saving (Review-II data integrity).
 */
@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock FavoriteRepository repo;
    @Mock RestaurantRepository restaurantRepo;
    @Mock MenuItemRepository menuItemRepo;
    FavoriteService service;

    @BeforeEach
    void setUp() {
        service = new FavoriteService(repo, restaurantRepo, menuItemRepo);
    }

    @Test
    void addRestaurant_valid_saves() {
        when(restaurantRepo.findById(1L)).thenReturn(Optional.of(Restaurant.builder().id(1L).build()));
        when(repo.save(any(Favorite.class))).thenAnswer(i -> i.getArgument(0));

        Favorite f = service.addRestaurant(7L, 1L);
        assertEquals(7L, f.getUserId());
        assertEquals(1L, f.getRestaurantId());
    }

    @Test
    void addRestaurant_missing_throws() {
        when(restaurantRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.addRestaurant(7L, 9L));
    }

    @Test
    void addMenuItem_missing_throws() {
        when(menuItemRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.addMenuItem(7L, 9L));
    }

    @Test
    void remove_delegatesToRepo() {
        service.remove(3L);
        verify(repo).deleteById(3L);
    }
}
