package com.ridelink.ride_management_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rides")
@Data
public class Ride {

    @Id
    private String id;

    private String passengerId;

    private String driverId;

    private String pickupLocation;

    private String destination;

    private String status = "REQUESTED";

    private Double estimatedFare = 0.0;

    private Double finalFare = 0.0;
}