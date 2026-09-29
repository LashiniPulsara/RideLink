package com.ridelink.driver_vehicle_service.controller;

import com.ridelink.driver_vehicle_service.model.Driver;
import com.ridelink.driver_vehicle_service.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(
            @Valid @RequestBody Driver driver) {

        if (driver.getDriverId() == null
                || driver.getDriverId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (driver.getName() == null
                || driver.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Driver savedDriver = driverService.createDriver(driver);

        return ResponseEntity.ok(savedDriver);
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                driverService.getDriverById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(
            @PathVariable String id,
            @RequestBody Driver driver) {

        return ResponseEntity.ok(
                driverService.updateDriver(id, driver)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(
            @PathVariable String id) {

        driverService.deleteDriver(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {

        return ResponseEntity.ok(
                driverService.getAvailableDrivers()
        );
    }

    @GetMapping("/eligible")
    public ResponseEntity<List<Driver>> getEligibleDrivers(
            @RequestParam String serviceArea) {

        if (serviceArea == null || serviceArea.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                driverService.getEligibleDrivers(serviceArea)
        );
    }
}