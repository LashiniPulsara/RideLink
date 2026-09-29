package com.ridelink.farepayment.service;

import com.ridelink.farepayment.model.Fare;
import com.ridelink.farepayment.repository.FareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FareService {

    private final FareRepository fareRepository;

    private static final double BASE_FARE = 200.0;
    private static final double RATE_PER_KM = 100.0;

    public FareService(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    // Existing fare estimation
    public Fare estimateFare(String rideId, double distance) {

        double totalFare = BASE_FARE + (distance * RATE_PER_KM);

        Fare fare = new Fare();
        fare.setRideId(rideId);
        fare.setDistance(distance);
        fare.setBaseFare(BASE_FARE);
        fare.setRatePerKm(RATE_PER_KM);
        fare.setTotalFare(totalFare);

        return fareRepository.save(fare);
    }

    // Existing final fare calculation
    public Fare calculateFinalFare(String rideId, double distance) {

        double totalFare = BASE_FARE + (distance * RATE_PER_KM);

        Fare fare = new Fare();
        fare.setRideId(rideId);
        fare.setDistance(distance);
        fare.setBaseFare(BASE_FARE);
        fare.setRatePerKm(RATE_PER_KM);
        fare.setTotalFare(totalFare);

        return fareRepository.save(fare);
    }

    // CREATE
    public Fare createFare(Fare fare) {
        return fareRepository.save(fare);
    }

    // READ - Get all
    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    // READ - Get by ID
    public Fare getFareById(String id) {
        return fareRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Fare not found"));
    }

    // UPDATE
    public Fare updateFare(String id, Fare updatedFare) {

        Fare existingFare = fareRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Fare not found"));

        existingFare.setRideId(updatedFare.getRideId());
        existingFare.setDistance(updatedFare.getDistance());
        existingFare.setBaseFare(updatedFare.getBaseFare());
        existingFare.setRatePerKm(updatedFare.getRatePerKm());
        existingFare.setTotalFare(updatedFare.getTotalFare());

        return fareRepository.save(existingFare);
    }

    // DELETE
    public void deleteFare(String id) {

        if (!fareRepository.existsById(id)) {
            throw new RuntimeException("Fare not found");
        }

        fareRepository.deleteById(id);
    }
}