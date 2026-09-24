package com.fooddelivery.service;

import com.fooddelivery.entity.MenuCategory;
import com.fooddelivery.repository.MenuCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Menu category logic: list, CRUD.
 * Why: keeps category mutations in one place for restaurant dashboard.
 */
@Service
public class CategoryService {
    private final MenuCategoryRepository repo;

    /**
     * Creates the service.
     * @param r category repository
     */
    public CategoryService(MenuCategoryRepository r) {
        this.repo = r;
    }

    /**
     * Lists all categories.
     * @return categories
     */
    public List<MenuCategory> getAll() {
        return repo.findAll();
    }

    /**
     * Gets a category by id.
     * @param id category id
     * @return category
     */
    public MenuCategory getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    /**
     * Creates a category.
     * @param c category to save
     * @return saved category
     */
    public MenuCategory create(MenuCategory c) {
        return repo.save(c);
    }

    /**
     * Updates name/description/image of a category.
     * @param id category id
     * @param c patch
     * @return saved category
     */
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