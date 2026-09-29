package com.ridelink.farepayment.service;

import com.ridelink.farepayment.model.Fare;
import com.ridelink.farepayment.repository.FareRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.MongoDatabaseFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FareServiceTest {

    @Mock
    private FareRepository fareRepository;

    @Mock
    private MongoDatabaseFactory mongoDatabaseFactory;

    @InjectMocks
    private FareService fareService;

    @Test
    void estimateFare_ShouldCalculateCorrectTotal() {

        Fare savedFare = new Fare();
        savedFare.setRideId("RIDE001");
        savedFare.setDistance(10);
        savedFare.setBaseFare(200);
        savedFare.setRatePerKm(100);
        savedFare.setTotalFare(1200);

        when(fareRepository.save(any(Fare.class)))
                .thenReturn(savedFare);

        Fare result = fareService.estimateFare(
                "RIDE001",
                10
        );

        assertEquals("RIDE001", result.getRideId());
        assertEquals(10, result.getDistance());
        assertEquals(200, result.getBaseFare());
        assertEquals(100, result.getRatePerKm());
        assertEquals(1200, result.getTotalFare());
    }

    @Test
    void calculateFinalFare_ShouldCalculateCorrectTotal() {

        Fare savedFare = new Fare();
        savedFare.setRideId("RIDE002");
        savedFare.setDistance(15);
        savedFare.setBaseFare(200);
        savedFare.setRatePerKm(100);
        savedFare.setTotalFare(1700);

        when(fareRepository.save(any(Fare.class)))
                .thenReturn(savedFare);

        Fare result = fareService.calculateFinalFare(
                "RIDE002",
                15
        );

        assertEquals("RIDE002", result.getRideId());
        assertEquals(15, result.getDistance());
        assertEquals(1700, result.getTotalFare());
    }
}