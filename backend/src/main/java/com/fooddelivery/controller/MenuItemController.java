package com.fooddelivery.controller;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/menu-items")
public class MenuItemController {
    private final MenuItemService service;
    public MenuItemController(MenuItemService s){ this.service=s; }

    @GetMapping
    public List<MenuItem> all(@RequestParam(required=false) String search, @RequestParam(required=false) Long restaurantId){
        if(restaurantId!=null) return service.byRestaurant(restaurantId);
        if(search!=null) return service.search(search);
        return service.getAll();
    }
    @GetMapping("/{id}")
    public MenuItem one(@PathVariable Long id){ return service.getById(id); }
    @PostMapping
    public MenuItem create(@RequestBody MenuItem m){ return service.create(m); }
    @PutMapping("/{id}")
    public MenuItem update(@PathVariable Long id, @RequestBody MenuItem m){ return service.update(id,m); }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.ok().build(); }

    // Restaurant menu alias
    @GetMapping("/restaurant/{rid}")
    public List<MenuItem> byRestaurant(@PathVariable Long rid){ return service.byRestaurant(rid); }
}
