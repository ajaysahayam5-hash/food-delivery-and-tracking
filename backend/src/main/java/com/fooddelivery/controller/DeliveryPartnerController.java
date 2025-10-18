package com.fooddelivery.controller;

import com.fooddelivery.entity.DeliveryPartner;
import com.fooddelivery.repository.DeliveryPartnerRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {
    private final DeliveryPartnerRepository partnerRepo;
    private final UserRepository userRepo;

    public DeliveryPartnerController(DeliveryPartnerRepository pr, UserRepository ur){
        this.partnerRepo=pr; this.userRepo=ur;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getByUser(Authentication auth){
        String email = auth.getName();
        DeliveryPartner partner = partnerRepo.findByEmail(email).orElse(null);
        if(partner == null){
            Long userId = userRepo.findByEmail(email).map(u -> u.getId()).orElse(null);
            if(userId != null){
                partner = partnerRepo.findByUserId(userId).orElse(null);
            }
        }
        if(partner == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(partner);
    }

    @GetMapping
    public Iterable<DeliveryPartner> all(){ return partnerRepo.findAll(); }

    @GetMapping("/{id}")
    public DeliveryPartner one(@PathVariable Long id){ return partnerRepo.findById(id).orElseThrow(); }

    @PostMapping
    public DeliveryPartner create(@RequestBody DeliveryPartner p){ return partnerRepo.save(p); }

    @PutMapping("/{id}")
    public DeliveryPartner update(@PathVariable Long id, @RequestBody DeliveryPartner p){
        DeliveryPartner existing = partnerRepo.findById(id).orElseThrow();
        existing.setFullName(p.getFullName());
        existing.setPhone(p.getPhone());
        existing.setEmail(p.getEmail());
        existing.setVehicleNumber(p.getVehicleNumber());
        existing.setVehicleType(p.getVehicleType());
        existing.setAvailability(p.getAvailability());
        existing.setCurrentLatitude(p.getCurrentLatitude());
        existing.setCurrentLongitude(p.getCurrentLongitude());
        return partnerRepo.save(existing);
    }
}