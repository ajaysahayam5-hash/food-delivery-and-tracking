package com.fooddelivery.service;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

/**
 * Order business logic: pricing, placement, status transitions, cancel rules.
 * Why: centralizes total calculation (items + fee), payment/delivery creation, and lifecycle guards.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final MenuItemRepository menuRepo;
    private final PaymentRepository paymentRepo;
    private final DeliveryRepository deliveryRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository or, OrderItemRepository oir, CartRepository cr, CartItemRepository cir, MenuItemRepository mr, PaymentRepository pr, DeliveryRepository dr, UserRepository ur){
        this.orderRepo=or; this.orderItemRepo=oir; this.cartRepo=cr; this.cartItemRepo=cir; this.menuRepo=mr; this.paymentRepo=pr; this.deliveryRepo=dr; this.userRepo=ur;
    }

    /**
     * Places an order from the user's cart, creates payment + delivery rows, clears cart.
     * @param customerId buyer id
     * @param restaurantId restaurant id (falls back to first cart item's restaurant)
     * @param deliveryAddress free-text address
     * @param addressId saved address id (nullable)
     * @param paymentMethod CASH/UPI/Card abstraction
     * @return saved order in PLACED status
     */
    @Transactional
    public Order placeOrder(Long customerId, Long restaurantId, String deliveryAddress, Long addressId, String paymentMethod){
        Cart cart=cartRepo.findByUserId(customerId).orElseThrow(() -> new RuntimeException("Cart empty"));
        List<CartItem> cartItems=cartItemRepo.findByCartId(cart.getId());
        if(cartItems.isEmpty()) throw new RuntimeException("Cart is empty");
        BigDecimal total=BigDecimal.ZERO;
        for(CartItem ci: cartItems){
            total = total.add(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
        }
        BigDecimal fee=new BigDecimal("40.00");
        total = total.add(fee);

        Order order=Order.builder()
                .customerId(customerId)
                .restaurantId(restaurantId!=null?restaurantId: menuRepo.findById(cartItems.get(0).getMenuItemId()).map(MenuItem::getRestaurantId).orElse(1L))
                .totalAmount(total)
                .deliveryFee(fee)
                .orderStatus("PLACED")
                .paymentStatus("Pending")
                .paymentMethod(paymentMethod!=null?paymentMethod:"CASH")
                .deliveryAddress(deliveryAddress)
                .deliveryAddressId(addressId)
                .build();
        order=orderRepo.save(order);
        for(CartItem ci: cartItems){
            OrderItem oi=OrderItem.builder().orderId(order.getId()).menuItemId(ci.getMenuItemId()).quantity(ci.getQuantity()).price(ci.getPrice()).build();
            orderItemRepo.save(oi);
        }
        Payment p=Payment.builder().orderId(order.getId()).paymentMethod(order.getPaymentMethod()).amount(total).transactionId("TXN"+System.currentTimeMillis()).paymentStatus(order.getPaymentMethod().equals("CASH")?"Pending":"Success").build();
        paymentRepo.save(p);
        Delivery d=Delivery.builder().orderId(order.getId()).trackingStatus("Assigned").deliveryAddress(deliveryAddress).build();
        deliveryRepo.save(d);
        cartItemRepo.deleteByCartId(cart.getId());
        return order;
    }

    public List<Order> myOrders(Long customerId){ return orderRepo.findByCustomerIdOrderByCreatedAtDesc(customerId); }
    public List<Order> restaurantOrders(Long restaurantId){ return orderRepo.findByRestaurantIdOrderByCreatedAtDesc(restaurantId); }
    public List<Order> allOrders(){ return orderRepo.findAll(); }
    public Order getById(Long id){ return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found")); }

    public Order updateStatus(Long orderId, String status){
        Order o=getById(orderId);
        // validate transition basic
        List<String> allowed=List.of("PLACED","CONFIRMED","PREPARING","READY_FOR_PICKUP","PICKED_UP","OUT_FOR_DELIVERY","DELIVERED","CANCELLED");
        if(!allowed.contains(status)) throw new RuntimeException("Invalid status");
        o.setOrderStatus(status);
        if(status.equals("DELIVERED")) o.setPaymentStatus("Paid");
        if(status.equals("CANCELLED")) o.setPaymentStatus("Failed");
        return orderRepo.save(o);
    }

    public void cancel(Long orderId, Long userId){
        Order o=getById(orderId);
        if(!o.getCustomerId().equals(userId)) throw new RuntimeException("Not your order");
        if(Set.of("OUT_FOR_DELIVERY","DELIVERED","CANCELLED","PICKED_UP").contains(o.getOrderStatus())) throw new RuntimeException("Cannot cancel at this stage");
        o.setOrderStatus("CANCELLED");
        orderRepo.save(o);
    }
}
