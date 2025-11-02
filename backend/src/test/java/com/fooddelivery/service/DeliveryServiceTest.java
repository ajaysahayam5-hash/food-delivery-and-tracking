package com.fooddelivery.service;

import com.fooddelivery.entity.Delivery;
import com.fooddelivery.entity.DeliveryLocation;
import com.fooddelivery.repository.DeliveryLocationRepository;
import com.fooddelivery.repository.DeliveryPartnerRepository;
import com.fooddelivery.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DeliveryService assign/status/location history.
 */
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock DeliveryRepository deliveryRepo;
    @Mock DeliveryLocationRepository locRepo;
    @Mock DeliveryPartnerRepository partnerRepo;

    DeliveryService service;

    @BeforeEach
    void setUp() { service = new DeliveryService(deliveryRepo, locRepo, partnerRepo); }

    @Test
    void assign_setsPartner() {
        Delivery d = Delivery.builder().id(1L).orderId(10L).trackingStatus("Created").build();
        when(deliveryRepo.findByOrderId(10L)).thenReturn(Optional.of(d));
        when(deliveryRepo.save(any(Delivery.class))).thenAnswer(i -> i.getArgument(0));
        Delivery out = service.assign(10L, 5L);
        assertEquals(5L, out.getDeliveryPartnerId());
    }

    @Test
    void updateStatus_ok() {
        Delivery d = Delivery.builder().id(1L).trackingStatus("Assigned").build();
        when(deliveryRepo.findById(1L)).thenReturn(Optional.of(d));
        when(deliveryRepo.save(any(Delivery.class))).thenAnswer(i -> i.getArgument(0));
        assertEquals("PICKED_UP", service.updateStatus(1L, "PICKED_UP").getTrackingStatus());
    }

    @Test
    void updateLocation_appendsHistory() {
        DeliveryLocation dl = DeliveryLocation.builder().id(1L).deliveryId(1L).build();
        when(locRepo.save(any(DeliveryLocation.class))).thenReturn(dl);
        DeliveryLocation out = service.updateLocation(1L, new BigDecimal("12.9"), new BigDecimal("77.6"), "On the way");
        assertEquals(1L, out.getDeliveryId());
    }

    @Test
    void history_returnsOrdered() {
        when(locRepo.findByDeliveryIdOrderByUpdatedAtDesc(1L)).thenReturn(List.of(DeliveryLocation.builder().id(1L).build()));
        assertEquals(1, service.history(1L).size());
    }

    @Test
    void getByOrder_missing_throws() {
        when(deliveryRepo.findByOrderId(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getByOrder(999L));
    }
}
