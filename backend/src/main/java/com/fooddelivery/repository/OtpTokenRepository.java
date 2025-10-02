package com.fooddelivery.repository;

import com.fooddelivery.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findTopByEmailOrderByCreatedAtDesc(String email);
}
