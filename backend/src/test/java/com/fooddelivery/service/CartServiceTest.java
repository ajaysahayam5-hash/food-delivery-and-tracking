package com.fooddelivery.service;

import com.fooddelivery.entity.Cart;
import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.CartItemRepository;
import com.fooddelivery.repository.CartRepository;
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
 * Unit tests for CartService add/update/remove flows.
 */
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock CartRepository cartRepo;
    @Mock CartItemRepository itemRepo;
    @Mock MenuItemRepository menuRepo;

    CartService service;

    @BeforeEach
    void setUp() {
        service = new CartService(cartRepo, itemRepo, menuRepo);
    }

    @Test
    void getOrCreate_existing_returnsIt() {
        Cart c = Cart.builder().id(1L).userId(9L).build();
        when(cartRepo.findByUserId(9L)).thenReturn(Optional.of(c));
        assertEquals(1L, service.getOrCreate(9L).getId());
    }

    @Test
    void addItem_new_createsWithMenuPrice() {
        Cart c = Cart.builder().id(1L).userId(9L).build();
        when(cartRepo.findByUserId(9L)).thenReturn(Optional.of(c));
        when(menuRepo.findById(5L)).thenReturn(Optional.of(MenuItem.builder().id(5L).price(new BigDecimal("150")).build()));
        when(itemRepo.findByCartIdAndMenuItemId(1L, 5L)).thenReturn(Optional.empty());
        when(itemRepo.save(any(CartItem.class))).thenAnswer(i -> i.getArgument(0));

        CartItem ci = service.addItem(9L, 5L, 2);
        assertEquals(2, ci.getQuantity());
        assertEquals(0, new BigDecimal("150").compareTo(ci.getPrice()));
    }

    @Test
    void addItem_existing_incrementsQty() {
        Cart c = Cart.builder().id(1L).userId(9L).build();
        CartItem existing = CartItem.builder().id(7L).cartId(1L).menuItemId(5L).quantity(1).price(new BigDecimal("150")).build();
        when(cartRepo.findByUserId(9L)).thenReturn(Optional.of(c));
        when(menuRepo.findById(5L)).thenReturn(Optional.of(MenuItem.builder().id(5L).price(new BigDecimal("150")).build()));
        when(itemRepo.findByCartIdAndMenuItemId(1L, 5L)).thenReturn(Optional.of(existing));
        when(itemRepo.save(any(CartItem.class))).thenAnswer(i -> i.getArgument(0));

        assertEquals(3, service.addItem(9L, 5L, 2).getQuantity());
    }

    @Test
    void getCart_enrichesWithMenu() {
        Cart c = Cart.builder().id(1L).userId(9L).build();
        when(cartRepo.findByUserId(9L)).thenReturn(Optional.of(c));
        CartItem ci = CartItem.builder().id(1L).cartId(1L).menuItemId(5L).quantity(1).price(new BigDecimal("10")).build();
        when(itemRepo.findByCartId(1L)).thenReturn(List.of(ci));
        when(menuRepo.findById(5L)).thenReturn(Optional.of(MenuItem.builder().id(5L).build()));

        assertEquals(1, service.getCart(9L).size());
    }
}
