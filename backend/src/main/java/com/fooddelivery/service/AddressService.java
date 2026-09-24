package com.fooddelivery.service;

import com.fooddelivery.entity.Address;
import com.fooddelivery.repository.AddressRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Address book logic: per-user CRUD with default-address handling and ownership guards.
 * Why: only one default address per user; updates check owner to protect PII.
 */
@Service
public class AddressService {
    private final AddressRepository repo;
    private final UserRepository userRepo;

    /**
     * Creates the service.
     * @param r address repository
     * @param ur user repository
     */
    public AddressService(AddressRepository r, UserRepository ur) {
        this.repo = r;
        this.userRepo = ur;
    }

    /**
     * Lists addresses for a user.
     * @param userId owner id
     * @return addresses
     */
    public List<Address> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    /**
     * Creates an address; clears other defaults if this is default.
     * @param userId owner id
     * @param a address to save
     * @return saved address
     */
    public Address create(Long userId, Address a) {
        a.setUserId(userId);
        if (a.getIsDefault() != null && a.getIsDefault()) {
            repo.findByUserId(userId).forEach(existing -> {
                existing.setIsDefault(false);
                repo.save(existing);
            });
        }
        return repo.save(a);
    }

    public Address update(Long userId, Long id, Address a) {
        Address existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        existing.setLabel(a.getLabel());
        existing.setFullAddress(a.getFullAddress());
        existing.setCity(a.getCity());
        existing.setPincode(a.getPincode());
        existing.setLatitude(a.getLatitude());
        existing.setLongitude(a.getLongitude());
        if (a.getIsDefault() != null && a.getIsDefault()) {
            repo.findByUserId(userId).forEach(addr -> {
                addr.setIsDefault(false);
                repo.save(addr);
            });
        }
        existing.setIsDefault(a.getIsDefault());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}