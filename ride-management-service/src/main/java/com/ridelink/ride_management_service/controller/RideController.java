package com.ridelink.ride_management_service.controller;

import com.ridelink.ride_management_service.model.Ride;
import com.ridelink.ride_management_service.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public Ride createRide(@RequestBody Ride ride) {
        return rideService.createRide(ride);
    }

    @GetMapping
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    @GetMapping("/{id}")
    public Optional<Ride> getRideById(@PathVariable String id) {
        return rideService.getRideById(id);
    }

    @PutMapping("/{id}/assign")
public Ride assignRide(@PathVariable String id) {
    return rideService.assignRide(id);
}

@PutMapping("/{id}/accept")
public Ride acceptRide(@PathVariable String id) {
    return rideService.acceptRide(id);
}

@PutMapping("/{id}/start")
public Ride startRide(@PathVariable String id) {
    return rideService.startRide(id);
}

@PutMapping("/{id}/complete")
public Ride completeRide(@PathVariable String id) {
    return rideService.completeRide(id);
}

@PutMapping("/{id}/cancel")
public Ride cancelRide(@PathVariable String id) {
    return rideService.cancelRide(id);
}

}