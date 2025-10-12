package com.fooddelivery.service;

import com.fooddelivery.entity.MenuCategory;
import com.fooddelivery.repository.MenuCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final MenuCategoryRepository repo;

    public CategoryService(MenuCategoryRepository r) {
        this.repo = r;
    }

    public List<MenuCategory> getAll() {
        return repo.findAll();
    }

    public MenuCategory getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public MenuCategory create(MenuCategory c) {
        return repo.save(c);
    }

    public MenuCategory update(Long id, MenuCategory c) {
        MenuCategory ex = getById(id);
        ex.setName(c.getName());
        ex.setDescription(c.getDescription());
        ex.setImageUrl(c.getImageUrl());
        return repo.save(ex);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}