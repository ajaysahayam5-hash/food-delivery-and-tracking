package com.fooddelivery.repository;

import com.fooddelivery.entity.DeliveryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocation, Long> {
    List<DeliveryLocation> findByDeliveryIdOrderByUpdatedAtDesc(Long deliveryId);
    Optional<DeliveryLocation> findTopByDeliveryIdOrderByUpdatedAtDesc(Long deliveryId);
}
