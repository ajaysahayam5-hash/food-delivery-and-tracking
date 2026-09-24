package com.fooddelivery.service;

import com.fooddelivery.entity.User;
import com.fooddelivery.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User profile business logic: lookup, list and safe update.
 * Why: password re-hashing is centralized here so controllers stay thin.
 */
@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    /**
     * Creates the service.
     * @param r user repository
     * @param pe BCrypt encoder
     */
    public UserService(UserRepository r, PasswordEncoder pe) {
        this.repo = r;
        this.encoder = pe;
    }

    /**
     * Lists all users (admin).
     * @return all users
     */
    public List<User> getAll() {
        return repo.findAll();
    }

    /**
     * Gets a user by id.
     * @param id user id
     * @return user
     */
    public User getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Gets a user by email.
     * @param email user email
     * @return user
     */
    public User getByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Updates profile fields; re-hashes password only if provided.
     * @param id user id
     * @param u patch with fullName/phone/password
     * @return saved user
     */
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