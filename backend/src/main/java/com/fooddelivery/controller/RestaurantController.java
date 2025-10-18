package com.fooddelivery.controller;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService service;
    public RestaurantController(RestaurantService s){ this.service=s; }

    @GetMapping
    public List<Restaurant> all(@RequestParam(required=false) String search){
        return search!=null? service.search(search): service.getAll();
    }
    @GetMapping("/{id}")
    public Restaurant one(@PathVariable Long id){ return service.getById(id); }
    @PostMapping
    public Restaurant create(@RequestBody Restaurant r){ return service.create(r); }
    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant r){ return service.update(id,r); }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.ok().build(); }
}
