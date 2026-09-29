package com.ridelink.driver_vehicle_service.service;

import com.ridelink.driver_vehicle_service.model.Driver;
import com.ridelink.driver_vehicle_service.repository.DriverRepository;
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
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    void createDriver_shouldSaveDriver() {

        Driver driver = new Driver();

        driver.setDriverId("DRV001");
        driver.setName("Ravindu");
        driver.setAvailable(true);
        driver.setServiceArea("Negombo");

        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverService.createDriver(driver);

        assertNotNull(result);
        assertEquals("DRV001", result.getDriverId());
        assertEquals("Ravindu", result.getName());

        verify(driverRepository).save(driver);
    }

    @Test
    void getAllDrivers_shouldReturnDrivers() {

        Driver driver = new Driver();
        driver.setDriverId("DRV001");
        driver.setName("Ravindu");

        when(driverRepository.findAll())
                .thenReturn(List.of(driver));

        List<Driver> result = driverService.getAllDrivers();

        assertEquals(1, result.size());
        assertEquals("DRV001", result.get(0).getDriverId());

        verify(driverRepository).findAll();
    }

    @Test
    void getDriverById_shouldReturnDriver() {

        Driver driver = new Driver();
        driver.setDriverId("DRV001");
        driver.setName("Ravindu");

        when(driverRepository.findById("123"))
                .thenReturn(Optional.of(driver));

        Driver result = driverService.getDriverById("123");

        assertNotNull(result);
        assertEquals("DRV001", result.getDriverId());

        verify(driverRepository).findById("123");
    }

    @Test
    void getAvailableDrivers_shouldReturnAvailableDrivers() {

        Driver driver = new Driver();
        driver.setDriverId("DRV001");
        driver.setAvailable(true);

        when(driverRepository.findByAvailableTrue())
                .thenReturn(List.of(driver));

        List<Driver> result =
                driverService.getAvailableDrivers();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());

        verify(driverRepository).findByAvailableTrue();
    }

    @Test
    void getEligibleDrivers_shouldReturnDriversByServiceArea() {

        Driver driver = new Driver();
        driver.setDriverId("DRV001");
        driver.setAvailable(true);
        driver.setServiceArea("Negombo");

        when(driverRepository
                .findByAvailableTrueAndServiceAreaIgnoreCase("Negombo"))
                .thenReturn(List.of(driver));

        List<Driver> result =
                driverService.getEligibleDrivers("Negombo");

        assertEquals(1, result.size());
        assertEquals("Negombo",
                result.get(0).getServiceArea());

        verify(driverRepository)
                .findByAvailableTrueAndServiceAreaIgnoreCase("Negombo");
    }
}