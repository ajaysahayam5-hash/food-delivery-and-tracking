package com.fooddelivery.service;

import com.fooddelivery.entity.MenuCategory;
import com.fooddelivery.repository.MenuCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CategoryService.
 * Why: categories power menu filtering; covers CRUD guards for Review-II.
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock MenuCategoryRepository repo;
    CategoryService service;

    @BeforeEach
    void setUp() {
        service = new CategoryService(repo);
    }

    @Test
    void getById_missing_throws() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(1L));
    }

    @Test
    void create_savesCategory() {
        MenuCategory c = MenuCategory.builder().name("South Indian").build();
        when(repo.save(any(MenuCategory.class))).thenAnswer(i -> i.getArgument(0));
        assertEquals("South Indian", service.create(c).getName());
    }

    @Test
    void update_copiesFields() {
        MenuCategory ex = MenuCategory.builder().id(2L).name("Old").build();
        when(repo.findById(2L)).thenReturn(Optional.of(ex));
        when(repo.save(any(MenuCategory.class))).thenAnswer(i -> i.getArgument(0));

        MenuCategory patch = MenuCategory.builder().name("New").description("d").imageUrl("u").build();
        assertEquals("New", service.update(2L, patch).getName());
    }

    @Test
    void delete_delegatesToRepo() {
        service.delete(3L);
        verify(repo).deleteById(3L);
    }
}
