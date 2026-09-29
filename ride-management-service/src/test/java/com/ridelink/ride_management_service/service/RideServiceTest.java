package com.ridelink.ride_management_service.service;

import com.ridelink.ride_management_service.model.Ride;
import com.ridelink.ride_management_service.repository.RideRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    private Ride ride;

    @BeforeEach
    void setUp() {
        ride = new Ride();
        ride.setId("R001");
        ride.setPassengerId("P001");
        ride.setPickupLocation("Malabe");
        ride.setDestination("Kaduwela");
        ride.setStatus("REQUESTED");
    }

    // Test 1 - Create Ride
    @Test
    void createRide_shouldSaveRide() {

        when(rideRepository.save(ride)).thenReturn(ride);

        Ride result = rideService.createRide(ride);

        assertNotNull(result);
        assertEquals("P001", result.getPassengerId());
        assertEquals("REQUESTED", result.getStatus());

        verify(rideRepository).save(ride);
    }

    // Test 2 - Assign Ride
    @Test
    void assignRide_shouldChangeStatusToAssigned() {

        when(rideRepository.findById("R001"))
                .thenReturn(Optional.of(ride));

        when(rideRepository.save(any(Ride.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ride result = rideService.assignRide("R001");

        assertEquals("ASSIGNED", result.getStatus());

        verify(rideRepository).save(ride);
    }

    // Test 3 - Accept Ride
    @Test
    void acceptRide_shouldChangeAssignedToAccepted() {

        ride.setStatus("ASSIGNED");

        when(rideRepository.findById("R001"))
                .thenReturn(Optional.of(ride));

        when(rideRepository.save(any(Ride.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ride result = rideService.acceptRide("R001");

        assertEquals("ACCEPTED", result.getStatus());

        verify(rideRepository).save(ride);
    }

    // Test 4 - Start Ride
    @Test
    void startRide_shouldChangeAcceptedToInProgress() {

        ride.setStatus("ACCEPTED");

        when(rideRepository.findById("R001"))
                .thenReturn(Optional.of(ride));

        when(rideRepository.save(any(Ride.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ride result = rideService.startRide("R001");

        assertEquals("IN_PROGRESS", result.getStatus());

        verify(rideRepository).save(ride);
    }

    // Test 5 - Complete Ride
    @Test
    void completeRide_shouldChangeInProgressToCompleted() {

        ride.setStatus("IN_PROGRESS");

        when(rideRepository.findById("R001"))
                .thenReturn(Optional.of(ride));

        when(rideRepository.save(any(Ride.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ride result = rideService.completeRide("R001");

        assertEquals("COMPLETED", result.getStatus());

        verify(rideRepository).save(ride);
    }

    // Test 6 - Cancel Ride
    @Test
    void cancelRide_shouldChangeStatusToCancelled() {

        when(rideRepository.findById("R001"))
                .thenReturn(Optional.of(ride));

        when(rideRepository.save(any(Ride.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ride result = rideService.cancelRide("R001");

        assertEquals("CANCELLED", result.getStatus());

        verify(rideRepository).save(ride);
    }

    // Test 7 - Invalid Status Transition
    @Test
    void acceptRide_shouldRejectInvalidTransition() {

        ride.setStatus("COMPLETED");

        when(rideRepository.findById("R001"))
                .thenReturn(Optional.of(ride));

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> rideService.acceptRide("R001")
                );

        assertEquals(
                "Invalid status transition",
                exception.getMessage()
        );

        verify(rideRepository, never()).save(any(Ride.class));
    }
}