package com.fooddelivery.service;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Menu item catalog logic: list, search, CRUD.
 * Why: search is case-insensitive; update copies mutable fields only.
 */
@Service
public class MenuItemService {
    private final MenuItemRepository repo;
    /**
     * Creates the service.
     * @param r menu item repository
     */
    public MenuItemService(MenuItemRepository r){ this.repo=r; }
    /**
     * Lists all menu items.
     * @return all items
     */
    public List<MenuItem> getAll(){ return repo.findAll(); }
    /**
     * Gets an item by id.
     * @param id item id
     * @return item
     */
    public MenuItem getById(Long id){ return repo.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found")); }
    /**
     * Lists items for a restaurant.
     * @param rid restaurant id
     * @return items
     */
    public List<MenuItem> byRestaurant(Long rid){ return repo.findByRestaurantId(rid); }
    /**
     * Searches by food name (case-insensitive).
     * @param q query, blank returns all
     * @return matching items
     */
    public List<MenuItem> search(String q){ return q==null||q.isBlank()? repo.findAll() : repo.findByFoodNameContainingIgnoreCase(q); }
    /**
     * Creates a menu item.
     * @param m item to save
     * @return saved item
     */
    public MenuItem create(MenuItem m){ return repo.save(m); }
    /**
     * Updates mutable fields of an item.
     * @param id item id
     * @param m patch
     * @return saved item
     */
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
