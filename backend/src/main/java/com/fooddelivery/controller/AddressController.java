package com.fooddelivery.controller;

import com.fooddelivery.entity.Address;
import com.fooddelivery.service.AddressService;
import com.fooddelivery.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService service;
    private final UserRepository userRepo;
    public AddressController(AddressService s, UserRepository ur){ this.service=s; this.userRepo=ur; }
    private Long uid(Authentication a){ return userRepo.findByEmail(a.getName()).orElseThrow().getId(); }

    @GetMapping
    public List<Address> my(Authentication auth){ return service.getByUser(uid(auth)); }
    @PostMapping
    public Address create(@RequestBody Address a, Authentication auth){
        return service.create(uid(auth), a);
    }
    @PutMapping("/{id}")
    public Address update(@PathVariable Long id, @RequestBody Address a, Authentication auth){
        return service.update(uid(auth), id, a);
    }
    @DeleteMapping("/{id}")
    public void del(@PathVariable Long id){ service.delete(id); }
}
