package com.ridelink.farepayment.service;

import com.ridelink.farepayment.exception.PaymentNotFoundException;
import com.ridelink.farepayment.model.Payment;
import com.ridelink.farepayment.model.Receipt;
import com.ridelink.farepayment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void createPayment_ShouldCreatePendingPayment() {

        Payment savedPayment = new Payment();

        savedPayment.setId("PAY001");
        savedPayment.setRideId("RIDE001");
        savedPayment.setAmount(1200);
        savedPayment.setPaymentMethod("CARD");
        savedPayment.setStatus("PENDING");

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(savedPayment);

        Payment result = paymentService.createPayment(
                "RIDE001",
                1200,
                "CARD"
        );

        assertEquals("PAY001", result.getId());
        assertEquals("RIDE001", result.getRideId());
        assertEquals(1200, result.getAmount());
        assertEquals("CARD", result.getPaymentMethod());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void updatePaymentStatus_ShouldUpdateStatus() {

        Payment payment = new Payment();

        payment.setId("PAY001");
        payment.setRideId("RIDE001");
        payment.setAmount(1200);
        payment.setPaymentMethod("CARD");
        payment.setStatus("PENDING");

        when(paymentRepository.findById("PAY001"))
                .thenReturn(Optional.of(payment));

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        Payment result = paymentService.updatePaymentStatus(
                "PAY001",
                "COMPLETED"
        );

        assertEquals("COMPLETED", result.getStatus());
    }

    @Test
    void updatePaymentStatus_ShouldThrowException_WhenPaymentNotFound() {

        when(paymentRepository.findById("INVALID"))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentNotFoundException.class,
                () -> paymentService.updatePaymentStatus(
                        "INVALID",
                        "COMPLETED"
                )
        );
    }

    @Test
    void generateReceipt_ShouldReturnCorrectReceipt() {

        Payment payment = new Payment();

        payment.setId("PAY001");
        payment.setRideId("RIDE001");
        payment.setAmount(1200);
        payment.setPaymentMethod("CARD");
        payment.setStatus("COMPLETED");
        payment.setPaymentDate("2026-09-23T10:00:00");

        when(paymentRepository.findById("PAY001"))
                .thenReturn(Optional.of(payment));

        Receipt receipt = paymentService.generateReceipt("PAY001");

        assertEquals("REC-PAY001", receipt.getReceiptId());
        assertEquals("PAY001", receipt.getPaymentId());
        assertEquals("RIDE001", receipt.getRideId());
        assertEquals(1200, receipt.getAmount());
        assertEquals("CARD", receipt.getPaymentMethod());
        assertEquals("COMPLETED", receipt.getPaymentStatus());
        assertEquals(
                "2026-09-23T10:00:00",
                receipt.getPaymentDate()
        );
    }
}