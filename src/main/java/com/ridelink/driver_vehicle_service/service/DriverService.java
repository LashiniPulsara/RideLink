package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.exception.ResourceNotFoundException;
import com.ridelink.driver_vehicle_service.model.Driver;
import com.ridelink.driver_vehicle_service.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(String id) {

        return driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver not found"
                        ));
    }

    public Driver updateDriver(
            String id,
            Driver updatedDriver) {

        Driver existingDriver =
                driverRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Driver not found"
                                ));

        existingDriver.setDriverId(
                updatedDriver.getDriverId()
        );

        existingDriver.setName(
                updatedDriver.getName()
        );

        existingDriver.setPhone(
                updatedDriver.getPhone()
        );

        existingDriver.setLicenseNumber(
                updatedDriver.getLicenseNumber()
        );

        existingDriver.setAvailable(
                updatedDriver.isAvailable()
        );

        existingDriver.setServiceArea(
                updatedDriver.getServiceArea()
        );

        existingDriver.setCurrentLatitude(
                updatedDriver.getCurrentLatitude()
        );

        existingDriver.setCurrentLongitude(
                updatedDriver.getCurrentLongitude()
        );

        return driverRepository.save(existingDriver);
    }

    public void deleteDriver(String id) {

        if (!driverRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Driver not found"
            );
        }

        driverRepository.deleteById(id);
    }

    public List<Driver> getAvailableDrivers() {

        return driverRepository.findByAvailableTrue();
    }

    public List<Driver> getEligibleDrivers(
            String serviceArea) {

        return driverRepository
                .findByAvailableTrueAndServiceAreaIgnoreCase(
                        serviceArea
                );
    }
}