package com.fooddelivery.service;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MenuItemService catalog logic.
 * Why: menu search/CRUD is core customer + restaurant functionality for Review-II.
 */
@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @Mock MenuItemRepository repo;
    MenuItemService service;

    @BeforeEach
    void setUp() {
        service = new MenuItemService(repo);
    }

    @Test
    void getById_found_returnsItem() {
        MenuItem m = MenuItem.builder().id(1L).foodName("Biryani").price(new BigDecimal("199.00")).build();
        when(repo.findById(1L)).thenReturn(Optional.of(m));
        assertEquals("Biryani", service.getById(1L).getFoodName());
    }

    @Test
    void getById_missing_throws() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(9L));
    }

    @Test
    void search_blankQuery_returnsAll() {
        when(repo.findAll()).thenReturn(List.of());
        assertNotNull(service.search("  "));
        verify(repo).findAll();
    }

    @Test
    void search_keyword_delegatesToRepo() {
        when(repo.findByFoodNameContainingIgnoreCase("pizza")).thenReturn(List.of());
        service.search("pizza");
        verify(repo).findByFoodNameContainingIgnoreCase("pizza");
    }

    @Test
    void update_copiesMutableFields() {
        MenuItem ex = MenuItem.builder().id(2L).foodName("Old").price(new BigDecimal("100")).build();
        when(repo.findById(2L)).thenReturn(Optional.of(ex));
        when(repo.save(any(MenuItem.class))).thenAnswer(i -> i.getArgument(0));

        MenuItem patch = MenuItem.builder().foodName("New").description("Tasty")
                .price(new BigDecimal("150")).imageUrl("u").categoryId(3L)
                .availability(false).isVeg(true).build();
        MenuItem updated = service.update(2L, patch);

        assertEquals("New", updated.getFoodName());
        assertEquals(0, new BigDecimal("150").compareTo(updated.getPrice()));
        assertEquals(false, updated.getAvailability());
    }

    @Test
    void delete_delegatesToRepo() {
        service.delete(5L);
        verify(repo).deleteById(5L);
    }
}
