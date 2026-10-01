package com.ridelink.ride_management_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverResponse {

    private String id;
    private String driverId;
}