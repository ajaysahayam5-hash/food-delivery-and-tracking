package com.fooddelivery.service;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository repo;
    public MenuItemService(MenuItemRepository r){ this.repo=r; }
    public List<MenuItem> getAll(){ return repo.findAll(); }
    public MenuItem getById(Long id){ return repo.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found")); }
    public List<MenuItem> byRestaurant(Long rid){ return repo.findByRestaurantId(rid); }
    public List<MenuItem> search(String q){ return q==null||q.isBlank()? repo.findAll() : repo.findByFoodNameContainingIgnoreCase(q); }
    public MenuItem create(MenuItem m){ return repo.save(m); }
    public MenuItem update(Long id, MenuItem m){
        MenuItem ex=getById(id);
        ex.setFoodName(m.getFoodName());
        ex.setDescription(m.getDescription());
        ex.setPrice(m.getPrice());
        ex.setImageUrl(m.getImageUrl());
        ex.setCategoryId(m.getCategoryId());
        ex.setAvailability(m.getAvailability());
        ex.setIsVeg(m.getIsVeg());
        return repo.save(ex);
    }
    public void delete(Long id){ repo.deleteById(id); }
}
