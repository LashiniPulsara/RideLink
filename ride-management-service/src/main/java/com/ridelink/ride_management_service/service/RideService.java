package com.ridelink.ride_management_service.service;

import com.ridelink.ride_management_service.client.DriverClient;
import com.ridelink.ride_management_service.dto.DriverResponse;
import com.ridelink.ride_management_service.model.Ride;
import com.ridelink.ride_management_service.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final DriverClient driverClient;

    public RideService(RideRepository rideRepository, DriverClient driverClient) {
        this.rideRepository = rideRepository;
        this.driverClient = driverClient;
    }

    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public Optional<Ride> getRideById(String id) {
        return rideRepository.findById(id);
    }

    public Ride assignRide(String id) {
        Ride ride = rideRepository.findById(id).orElseThrow();
        DriverResponse driver = driverClient.getAvailableDriver();
        ride.setDriverId(driver.getDriverId());
        ride.setStatus("ASSIGNED");
        return rideRepository.save(ride);
    }

public Ride acceptRide(String id) {
    Ride ride = rideRepository.findById(id).orElseThrow();

    if (!ride.getStatus().equals("ASSIGNED")) {
        throw new RuntimeException("Invalid status transition");
    }

    ride.setStatus("ACCEPTED");
    return rideRepository.save(ride);
}

public Ride startRide(String id) {
    Ride ride = rideRepository.findById(id).orElseThrow();

    if (!ride.getStatus().equals("ACCEPTED")) {
        throw new RuntimeException("Invalid status transition");
    }

    ride.setStatus("IN_PROGRESS");
    return rideRepository.save(ride);
}

public Ride completeRide(String id) {
    Ride ride = rideRepository.findById(id).orElseThrow();

    if (!ride.getStatus().equals("IN_PROGRESS")) {
        throw new RuntimeException("Invalid status transition");
    }

    ride.setStatus("COMPLETED");
    return rideRepository.save(ride);
}

public Ride cancelRide(String id) {
    Ride ride = rideRepository.findById(id).orElseThrow();

    ride.setStatus("CANCELLED");
    return rideRepository.save(ride);
}
}