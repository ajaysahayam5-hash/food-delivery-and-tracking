package com.fooddelivery.service;

import com.fooddelivery.entity.Address;
import com.fooddelivery.repository.AddressRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository repo;
    private final UserRepository userRepo;

    public AddressService(AddressRepository r, UserRepository ur) {
        this.repo = r;
        this.userRepo = ur;
    }

    public List<Address> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

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