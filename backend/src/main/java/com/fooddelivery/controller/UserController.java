package com.fooddelivery.controller;

import com.fooddelivery.entity.User;
import com.fooddelivery.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    public UserController(UserService s){ this.service=s; }
    @GetMapping("/me")
    public User me(Authentication auth){ return service.getByEmail(auth.getName()); }
    @GetMapping
    public List<User> all(){ return service.getAll(); }
    @GetMapping("/{id}")
    public User one(@PathVariable Long id){ return service.getById(id); }
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User u){ return service.update(id, u); }
}
