package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.model.Vehicle;
import com.ridelink.driver_vehicle_service.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void createVehicle_shouldSaveVehicle() {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleId("VEH001");
        vehicle.setDriverId("DRV001");
        vehicle.setVehicleNumber("CAB-1234");
        vehicle.setVehicleType("Car");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Axio");
        vehicle.setSeatCount(4);

        when(vehicleRepository.save(vehicle))
                .thenReturn(vehicle);

        Vehicle result =
                vehicleService.createVehicle(vehicle);

        assertNotNull(result);
        assertEquals("VEH001", result.getVehicleId());
        assertEquals("DRV001", result.getDriverId());

        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void getAllVehicles_shouldReturnVehicles() {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleId("VEH001");
        vehicle.setDriverId("DRV001");

        when(vehicleRepository.findAll())
                .thenReturn(List.of(vehicle));

        List<Vehicle> result =
                vehicleService.getAllVehicles();

        assertEquals(1, result.size());
        assertEquals(
                "VEH001",
                result.get(0).getVehicleId()
        );

        verify(vehicleRepository).findAll();
    }

    @Test
    void getVehicleById_shouldReturnVehicle() {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleId("VEH001");
        vehicle.setDriverId("DRV001");

        when(vehicleRepository.findById("123"))
                .thenReturn(Optional.of(vehicle));

        Vehicle result =
                vehicleService.getVehicleById("123");

        assertNotNull(result);
        assertEquals(
                "VEH001",
                result.getVehicleId()
        );

        verify(vehicleRepository).findById("123");
    }

    @Test
    void getVehicleByDriverId_shouldReturnVehicle() {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleId("VEH001");
        vehicle.setDriverId("DRV001");
        vehicle.setVehicleNumber("CAB-1234");

        when(vehicleRepository.findByDriverId("DRV001"))
                .thenReturn(Optional.of(vehicle));

        Vehicle result =
                vehicleService.getVehicleByDriverId("DRV001");

        assertNotNull(result);
        assertEquals(
                "VEH001",
                result.getVehicleId()
        );
        assertEquals(
                "DRV001",
                result.getDriverId()
        );

        verify(vehicleRepository)
                .findByDriverId("DRV001");
    }
}