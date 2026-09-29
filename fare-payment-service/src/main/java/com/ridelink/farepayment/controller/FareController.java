package com.ridelink.farepayment.controller;

import com.ridelink.farepayment.model.Fare;
import com.ridelink.farepayment.service.FareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fares")
public class FareController {

    private final FareService fareService;

    public FareController(FareService fareService) {
        this.fareService = fareService;
    }

    // =========================
    // EXISTING FARE CALCULATION
    // =========================

    @PostMapping("/estimate")
    public ResponseEntity<Fare> estimateFare(
            @RequestParam String rideId,
            @RequestParam double distance) {

        if (rideId == null || rideId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (distance <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Fare fare = fareService.estimateFare(rideId, distance);

        return ResponseEntity.ok(fare);
    }

    @PostMapping("/final")
    public ResponseEntity<Fare> calculateFinalFare(
            @RequestParam String rideId,
            @RequestParam double distance) {

        if (rideId == null || rideId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (distance <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Fare fare = fareService.calculateFinalFare(rideId, distance);

        return ResponseEntity.ok(fare);
    }

    // =========================
    // CRUD OPERATIONS
    // =========================

    // CREATE
    @PostMapping
    public ResponseEntity<Fare> createFare(
            @RequestBody Fare fare) {

        if (fare.getRideId() == null
                || fare.getRideId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (fare.getDistance() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Fare savedFare = fareService.createFare(fare);

        return ResponseEntity.ok(savedFare);
    }

    // READ - Get all fares
    @GetMapping
    public ResponseEntity<List<Fare>> getAllFares() {

        return ResponseEntity.ok(
                fareService.getAllFares()
        );
    }

    // READ - Get fare by ID
    @GetMapping("/{id}")
    public ResponseEntity<Fare> getFareById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                fareService.getFareById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Fare> updateFare(
            @PathVariable String id,
            @RequestBody Fare fare) {

        if (fare.getRideId() == null
                || fare.getRideId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (fare.getDistance() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                fareService.updateFare(id, fare)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFare(
            @PathVariable String id) {

        fareService.deleteFare(id);

        return ResponseEntity.noContent().build();
    }
}