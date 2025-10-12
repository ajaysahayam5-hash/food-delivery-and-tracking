package com.fooddelivery.service;

import com.fooddelivery.entity.User;
import com.fooddelivery.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository r, PasswordEncoder pe) {
        this.repo = r;
        this.encoder = pe;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User update(Long id, User u) {
        User existing = getById(id);
        existing.setFullName(u.getFullName());
        existing.setPhone(u.getPhone());
        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            existing.setPassword(encoder.encode(u.getPassword()));
        }
        return repo.save(existing);
    }
}