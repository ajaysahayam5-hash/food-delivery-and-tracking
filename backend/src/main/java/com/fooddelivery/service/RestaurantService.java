package com.fooddelivery.service;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Restaurant catalog logic: list, search, CRUD, owner lookup.
 * Why: search is case-insensitive name match; update copies mutable fields only.
 */
@Service
public class RestaurantService {
    private final RestaurantRepository repo;
    public RestaurantService(RestaurantRepository r){ this.repo=r; }
    public List<Restaurant> getAll(){ return repo.findAll(); }
    public List<Restaurant> search(String q){
        if(q==null||q.isBlank()) return repo.findAll();
        return repo.findByRestaurantNameContainingIgnoreCase(q);
    }
    public Restaurant getById(Long id){ return repo.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found")); }
    public Restaurant create(Restaurant r){ return repo.save(r); }
    public Restaurant update(Long id, Restaurant r){
        Restaurant ex = getById(id);
        ex.setRestaurantName(r.getRestaurantName());
        ex.setDescription(r.getDescription());
        ex.setAddress(r.getAddress());
        ex.setCity(r.getCity());
        ex.setPhone(r.getPhone());
        ex.setEmail(r.getEmail());
        ex.setImageUrl(r.getImageUrl());
        ex.setStatus(r.getStatus());
        ex.setRating(r.getRating());
        return repo.save(ex);
    }
    public void delete(Long id){ repo.deleteById(id); }
    public List<Restaurant> byOwner(Long ownerId){ return repo.findByOwnerId(ownerId); }
}
