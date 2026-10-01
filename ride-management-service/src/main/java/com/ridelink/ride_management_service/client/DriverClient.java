package com.ridelink.ride_management_service.client;

import com.ridelink.ride_management_service.dto.DriverResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class DriverClient {

    private final RestClient restClient = RestClient.create("http://localhost:8081");

    public DriverResponse getAvailableDriver() {
        List<DriverResponse> drivers = restClient.get()
                .uri("/api/drivers/available")
                .retrieve()
                .body(new ParameterizedTypeReference<List<DriverResponse>>() {});

        if (drivers == null || drivers.isEmpty() || drivers.get(0) == null) {
            throw new IllegalStateException("No available driver found");
        }

        DriverResponse driver = drivers.get(0);
        String driverId = driver.getDriverId();
        if (driverId == null || driverId.isBlank()) {
            driverId = driver.getId();
        }
        if (driverId == null || driverId.isBlank()) {
            throw new IllegalStateException("Available driver has no driver ID");
        }

        driver.setDriverId(driverId);
        return driver;
    }
}