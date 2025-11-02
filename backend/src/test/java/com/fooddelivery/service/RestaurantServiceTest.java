package com.fooddelivery.service;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RestaurantService search and CRUD.
 */
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock RestaurantRepository repo;
    RestaurantService service;

    @BeforeEach
    void setUp() { service = new RestaurantService(repo); }

    @Test
    void search_blank_returnsAll() {
        when(repo.findAll()).thenReturn(List.of(Restaurant.builder().id(1L).build()));
        assertEquals(1, service.search("  ").size());
    }

    @Test
    void search_query_filtersByName() {
        when(repo.findByRestaurantNameContainingIgnoreCase("spice")).thenReturn(List.of(Restaurant.builder().id(2L).build()));
        assertEquals(1, service.search("spice").size());
    }

    @Test
    void getById_missing_throws() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(99L));
    }

    @Test
    void update_copiesFields() {
        Restaurant ex = Restaurant.builder().id(1L).restaurantName("Old").city("A").build();
        when(repo.findById(1L)).thenReturn(Optional.of(ex));
        when(repo.save(any(Restaurant.class))).thenAnswer(i -> i.getArgument(0));
        Restaurant upd = Restaurant.builder().restaurantName("New").description("d").address("a").city("B").phone("p").email("e").imageUrl("u").status("Open").rating(4.5).build();
        Restaurant out = service.update(1L, upd);
        assertEquals("New", out.getRestaurantName());
        assertEquals("B", out.getCity());
    }
}
