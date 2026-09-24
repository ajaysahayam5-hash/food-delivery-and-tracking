package com.fooddelivery.service;

import com.fooddelivery.entity.Favorite;
import com.fooddelivery.repository.FavoriteRepository;
import com.fooddelivery.repository.MenuItemRepository;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Favorites logic: save restaurant/menu favorites after validating target exists.
 * Why: prevents orphan favorites pointing to deleted restaurants or menu items.
 */
@Service
public class FavoriteService {
    private final FavoriteRepository repo;
    private final RestaurantRepository restaurantRepo;
    private final MenuItemRepository menuItemRepo;

    /**
     * Creates the service.
     * @param r favorite repository
     * @param rr restaurant repository
     * @param mir menu item repository
     */
    public FavoriteService(FavoriteRepository r, RestaurantRepository rr, MenuItemRepository mir) {
        this.repo = r;
        this.restaurantRepo = rr;
        this.menuItemRepo = mir;
    }

    /**
     * Lists favorites for a user.
     * @param userId owner id
     * @return favorites
     */
    public List<Favorite> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    /**
     * Adds a restaurant favorite after existence check.
     * @param userId owner id
     * @param restaurantId restaurant id
     * @return saved favorite
     */
    public Favorite addRestaurant(Long userId, Long restaurantId) {
        restaurantRepo.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        Favorite f = Favorite.builder().userId(userId).restaurantId(restaurantId).build();
        return repo.save(f);
    }

    public Favorite addMenuItem(Long userId, Long menuItemId) {
        menuItemRepo.findById(menuItemId).orElseThrow(() -> new RuntimeException("Menu item not found"));
        Favorite f = Favorite.builder().userId(userId).menuItemId(menuItemId).build();
        return repo.save(f);
    }

    public void remove(Long id) {
        repo.deleteById(id);
    }
}