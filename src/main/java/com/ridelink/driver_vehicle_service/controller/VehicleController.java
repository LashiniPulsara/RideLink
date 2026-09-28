package com.ridelink.driver_vehicle_service.controller;

import com.ridelink.driver_vehicle_service.model.Vehicle;
import com.ridelink.driver_vehicle_service.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(
            @Valid @RequestBody Vehicle vehicle) {

        if (vehicle.getVehicleId() == null
                || vehicle.getVehicleId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (vehicle.getDriverId() == null
                || vehicle.getDriverId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Vehicle savedVehicle =
                vehicleService.createVehicle(vehicle);

        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(
                vehicleService.getAllVehicles()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                vehicleService.getVehicleById(id)
        );
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Vehicle> getVehicleByDriverId(
            @PathVariable String driverId) {

        return ResponseEntity.ok(
                vehicleService.getVehicleByDriverId(driverId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable String id,
            @RequestBody Vehicle vehicle) {

        return ResponseEntity.ok(
                vehicleService.updateVehicle(id, vehicle)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable String id) {

        vehicleService.deleteVehicle(id);

        return ResponseEntity.noContent().build();
    }
}