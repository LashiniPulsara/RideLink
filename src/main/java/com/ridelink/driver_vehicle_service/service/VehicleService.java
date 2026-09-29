package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.exception.ResourceNotFoundException;
import com.ridelink.driver_vehicle_service.model.Vehicle;
import com.ridelink.driver_vehicle_service.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(String id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found"
                        ));
    }

    public Vehicle getVehicleByDriverId(String driverId) {

        return vehicleRepository.findByDriverId(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found for driver"
                        ));
    }

    public Vehicle updateVehicle(
            String id,
            Vehicle updatedVehicle) {

        Vehicle existingVehicle =
                vehicleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vehicle not found"
                                ));

        existingVehicle.setVehicleId(
                updatedVehicle.getVehicleId()
        );

        existingVehicle.setDriverId(
                updatedVehicle.getDriverId()
        );

        existingVehicle.setVehicleNumber(
                updatedVehicle.getVehicleNumber()
        );

        existingVehicle.setVehicleType(
                updatedVehicle.getVehicleType()
        );

        existingVehicle.setBrand(
                updatedVehicle.getBrand()
        );

        existingVehicle.setModel(
                updatedVehicle.getModel()
        );

        existingVehicle.setSeatCount(
                updatedVehicle.getSeatCount()
        );

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(String id) {

        if (!vehicleRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Vehicle not found"
            );
        }

        vehicleRepository.deleteById(id);
    }
}