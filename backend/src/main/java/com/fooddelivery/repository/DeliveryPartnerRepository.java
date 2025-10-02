package com.fooddelivery.repository;

import com.fooddelivery.entity.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {
    Optional<DeliveryPartner> findByUserId(Long userId);
    Optional<DeliveryPartner> findByEmail(String email);
}
