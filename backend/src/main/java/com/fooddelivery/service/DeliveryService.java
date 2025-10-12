package com.fooddelivery.service;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * Delivery tracking logic: assign partner, status, GPS location history.
 * Why: location writes are append-only history; latest powers the Leaflet live map.
 */
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepo;
    private final DeliveryLocationRepository locRepo;
    private final DeliveryPartnerRepository partnerRepo;

    public DeliveryService(DeliveryRepository dr, DeliveryLocationRepository lr, DeliveryPartnerRepository pr){ this.deliveryRepo=dr; this.locRepo=lr; this.partnerRepo=pr; }

    public Delivery assign(Long orderId, Long partnerId){
        Delivery d = deliveryRepo.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Delivery not found"));
        d.setDeliveryPartnerId(partnerId);
        d.setTrackingStatus("Assigned");
        return deliveryRepo.save(d);
    }

    public Delivery updateStatus(Long deliveryId, String status){
        Delivery d = deliveryRepo.findById(deliveryId).orElseThrow(() -> new RuntimeException("Delivery not found"));
        d.setTrackingStatus(status);
        return deliveryRepo.save(d);
    }

    public DeliveryLocation updateLocation(Long deliveryId, BigDecimal lat, BigDecimal lon, String loc){
        DeliveryLocation dl = DeliveryLocation.builder().deliveryId(deliveryId).latitude(lat).longitude(lon).currentLocation(loc).build();
        return locRepo.save(dl);
    }

    public DeliveryLocation latestLocation(Long deliveryId){
        return locRepo.findTopByDeliveryIdOrderByUpdatedAtDesc(deliveryId).orElse(null);
    }

    public List<DeliveryLocation> history(Long deliveryId){
        return locRepo.findByDeliveryIdOrderByUpdatedAtDesc(deliveryId);
    }

    public Delivery getByOrder(Long orderId){
        return deliveryRepo.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    public Delivery getById(Long id){ return deliveryRepo.findById(id).orElseThrow(() -> new RuntimeException("Delivery not found")); }
    public List<Delivery> byPartner(Long pid){ return deliveryRepo.findByDeliveryPartnerId(pid); }
    public List<Delivery> all(){ return deliveryRepo.findAll(); }
}
