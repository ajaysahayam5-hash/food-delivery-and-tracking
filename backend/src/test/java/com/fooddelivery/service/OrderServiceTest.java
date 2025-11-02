package com.fooddelivery.service;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
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
 * Unit tests for OrderService pricing, placement and lifecycle guards.
 * Why: order total + status transitions are core business logic for Review-II.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock OrderRepository orderRepo;
    @Mock OrderItemRepository orderItemRepo;
    @Mock CartRepository cartRepo;
    @Mock CartItemRepository cartItemRepo;
    @Mock MenuItemRepository menuRepo;
    @Mock PaymentRepository paymentRepo;
    @Mock DeliveryRepository deliveryRepo;
    @Mock UserRepository userRepo;

    OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService(orderRepo, orderItemRepo, cartRepo, cartItemRepo, menuRepo, paymentRepo, deliveryRepo, userRepo);
    }

    @Test
    void placeOrder_calculatesTotalPlusFee_andClearsCart() {
        Cart cart = Cart.builder().id(10L).userId(1L).build();
        CartItem ci = CartItem.builder().id(1L).cartId(10L).menuItemId(7L).quantity(2).price(new BigDecimal("100.00")).build();
        when(cartRepo.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepo.findByCartId(10L)).thenReturn(List.of(ci));
        when(orderRepo.save(any(Order.class))).thenAnswer(i -> { Order o = i.getArgument(0); o.setId(99L); return o; });

        Order o = service.placeOrder(1L, 3L, "Addr", null, "CASH");

        assertEquals(0, new BigDecimal("240.00").compareTo(o.getTotalAmount()));
        assertEquals("PLACED", o.getOrderStatus());
        verify(paymentRepo).save(any(Payment.class));
        verify(deliveryRepo).save(any(Delivery.class));
        verify(cartItemRepo).deleteByCartId(10L);
    }

    @Test
    void placeOrder_emptyCart_throws() {
        Cart cart = Cart.builder().id(11L).userId(2L).build();
        when(cartRepo.findByUserId(2L)).thenReturn(Optional.of(cart));
        when(cartItemRepo.findByCartId(11L)).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> service.placeOrder(2L, 1L, "A", null, "CASH"));
    }

    @Test
    void updateStatus_valid_movesToPreparing() {
        Order o = Order.builder().id(1L).orderStatus("CONFIRMED").build();
        when(orderRepo.findById(1L)).thenReturn(Optional.of(o));
        when(orderRepo.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        Order updated = service.updateStatus(1L, "PREPARING");
        assertEquals("PREPARING", updated.getOrderStatus());
    }

    @Test
    void updateStatus_invalid_throws() {
        Order o = Order.builder().id(1L).orderStatus("PLACED").build();
        when(orderRepo.findById(1L)).thenReturn(Optional.of(o));
        assertThrows(RuntimeException.class, () -> service.updateStatus(1L, "FLYING"));
    }

    @Test
    void updateStatus_delivered_marksPaid() {
        Order o = Order.builder().id(2L).orderStatus("OUT_FOR_DELIVERY").paymentStatus("Pending").build();
        when(orderRepo.findById(2L)).thenReturn(Optional.of(o));
        when(orderRepo.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        Order updated = service.updateStatus(2L, "DELIVERED");
        assertEquals("Paid", updated.getPaymentStatus());
    }

    @Test
    void cancel_lateStage_throws() {
        Order o = Order.builder().id(3L).customerId(1L).orderStatus("OUT_FOR_DELIVERY").build();
        when(orderRepo.findById(3L)).thenReturn(Optional.of(o));
        assertThrows(RuntimeException.class, () -> service.cancel(3L, 1L));
    }

    @Test
    void cancel_earlyStage_ok() {
        Order o = Order.builder().id(4L).customerId(1L).orderStatus("PLACED").build();
        when(orderRepo.findById(4L)).thenReturn(Optional.of(o));
        service.cancel(4L, 1L);
        assertEquals("CANCELLED", o.getOrderStatus());
    }
}
