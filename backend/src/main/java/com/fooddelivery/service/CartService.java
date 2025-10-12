package com.fooddelivery.service;

import com.fooddelivery.entity.Cart;
import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.CartItemRepository;
import com.fooddelivery.repository.CartRepository;
import com.fooddelivery.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Cart business logic: get-or-create, enriched view, add/update/remove/clear.
 * Why: cart is per-user; prices snapshotted from menu at add time.
 */
@Service
public class CartService {
    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final MenuItemRepository menuRepo;

    public CartService(CartRepository cr, CartItemRepository ir, MenuItemRepository mr){ this.cartRepo=cr; this.itemRepo=ir; this.menuRepo=mr; }

    public Cart getOrCreate(Long userId){
        return cartRepo.findByUserId(userId).orElseGet(() -> cartRepo.save(Cart.builder().userId(userId).build()));
    }

    public List<Map<String,Object>> getCart(Long userId){
        Cart cart = getOrCreate(userId);
        List<CartItem> items = itemRepo.findByCartId(cart.getId());
        List<Map<String,Object>> out=new ArrayList<>();
        for(CartItem ci: items){
            MenuItem mi = menuRepo.findById(ci.getMenuItemId()).orElse(null);
            Map<String,Object> m=new HashMap<>();
            m.put("cartItem", ci);
            m.put("menuItem", mi);
            out.add(m);
        }
        return out;
    }

    public CartItem addItem(Long userId, Long menuItemId, Integer qty){
        Cart cart=getOrCreate(userId);
        MenuItem mi=menuRepo.findById(menuItemId).orElseThrow(() -> new RuntimeException("Food not found"));
        Optional<CartItem> existing=itemRepo.findByCartIdAndMenuItemId(cart.getId(), menuItemId);
        if(existing.isPresent()){
            CartItem ci=existing.get();
            ci.setQuantity(ci.getQuantity()+ (qty!=null?qty:1));
            return itemRepo.save(ci);
        }
        CartItem ci=CartItem.builder().cartId(cart.getId()).menuItemId(menuItemId).quantity(qty!=null?qty:1).price(mi.getPrice()).build();
        return itemRepo.save(ci);
    }

    public CartItem updateQty(Long userId, Long itemId, Integer qty){
        CartItem ci=itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        ci.setQuantity(qty);
        return itemRepo.save(ci);
    }

    public void removeItem(Long userId, Long itemId){
        itemRepo.deleteById(itemId);
    }

    public void clear(Long userId){
        Cart c=getOrCreate(userId);
        itemRepo.deleteByCartId(c.getId());
    }
}
