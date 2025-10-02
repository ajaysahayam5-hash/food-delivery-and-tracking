package com.fooddelivery.repository;

import com.fooddelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCityContainingIgnoreCase(String city);
    List<Restaurant> findByRestaurantNameContainingIgnoreCase(String name);
    List<Restaurant> findByStatus(String status);
    List<Restaurant> findByOwnerId(Long ownerId);
}
