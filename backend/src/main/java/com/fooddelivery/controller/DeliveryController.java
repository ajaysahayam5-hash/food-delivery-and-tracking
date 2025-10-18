package com.fooddelivery.controller;

import com.fooddelivery.entity.*;
import com.fooddelivery.service.DeliveryService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService service;
    public DeliveryController(DeliveryService s){ this.service=s; }

    @GetMapping
    public List<Delivery> all(){ return service.all(); }

    @GetMapping("/{id}")
    public Delivery one(@PathVariable Long id){ return service.getById(id); }

    @GetMapping("/order/{orderId}")
    public Delivery byOrder(@PathVariable Long orderId){ return service.getByOrder(orderId); }

    @PutMapping("/{id}/status")
    public Delivery status(@PathVariable Long id, @RequestBody Map<String,String> body){ return service.updateStatus(id, body.get("status")); }

    @PostMapping("/{id}/location")
    public DeliveryLocation loc(@PathVariable Long id, @RequestBody Map<String,Object> body){
        BigDecimal lat=new BigDecimal(body.get("latitude").toString());
        BigDecimal lon=new BigDecimal(body.get("longitude").toString());
        String cur=(String) body.getOrDefault("currentLocation","On the way");
        return service.updateLocation(id, lat, lon, cur);
    }

    @GetMapping("/{id}/location")
    public Object latest(@PathVariable Long id){ return service.latestLocation(id); }

    @GetMapping("/{id}/history")
    public Object hist(@PathVariable Long id){ return service.history(id); }

    @PostMapping("/{id}/assign/{partnerId}")
    public Delivery assign(@PathVariable Long id, @PathVariable Long partnerId){ return service.assign(service.getById(id).getOrderId(), partnerId); }

    @GetMapping("/partner/{partnerId}")
    public List<Delivery> byPartner(@PathVariable Long partnerId){ return service.byPartner(partnerId); }
}
