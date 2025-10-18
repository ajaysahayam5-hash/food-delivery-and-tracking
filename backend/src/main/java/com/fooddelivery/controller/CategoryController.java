package com.fooddelivery.controller;

import com.fooddelivery.entity.MenuCategory;
import com.fooddelivery.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;
    public CategoryController(CategoryService s){ this.service=s; }
    @GetMapping public List<MenuCategory> all(){ return service.getAll(); }
    @GetMapping("/{id}") public MenuCategory one(@PathVariable Long id){ return service.getById(id); }
    @PostMapping public MenuCategory create(@RequestBody MenuCategory c){ return service.create(c); }
    @PutMapping("/{id}") public MenuCategory update(@PathVariable Long id, @RequestBody MenuCategory c){ return service.update(id, c); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.delete(id); }
}
