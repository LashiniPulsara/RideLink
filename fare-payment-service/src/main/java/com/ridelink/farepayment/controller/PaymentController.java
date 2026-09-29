package com.ridelink.farepayment.controller;

import com.ridelink.farepayment.model.Receipt;
import com.ridelink.farepayment.model.Payment;
import com.ridelink.farepayment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // =========================
    // EXISTING CREATE PAYMENT
    // =========================

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam String rideId,
            @RequestParam double amount,
            @RequestParam String paymentMethod) {

        if (rideId == null || rideId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (amount <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (paymentMethod == null || paymentMethod.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Payment payment = paymentService.createPayment(
                rideId,
                amount,
                paymentMethod
        );

        return ResponseEntity.ok(payment);
    }

    // =========================
    // EXISTING STATUS UPDATE
    // =========================

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable String paymentId,
            @RequestParam String status) {

        if (paymentId == null || paymentId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (status == null || status.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (!status.equals("PENDING")
                && !status.equals("COMPLETED")
                && !status.equals("FAILED")) {
            return ResponseEntity.badRequest().build();
        }

        Payment payment = paymentService.updatePaymentStatus(
                paymentId,
                status
        );

        return ResponseEntity.ok(payment);
    }

    // =========================
    // EXISTING RECEIPT
    // =========================

    @GetMapping("/{paymentId}/receipt")
    public ResponseEntity<Receipt> getPaymentReceipt(
            @PathVariable String paymentId) {

        if (paymentId == null || paymentId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Receipt receipt = paymentService.generateReceipt(paymentId);

        return ResponseEntity.ok(receipt);
    }

    // =========================
    // CRUD OPERATIONS
    // =========================

    // CREATE - JSON body
    @PostMapping("/record")
    public ResponseEntity<Payment> createPaymentRecord(
            @RequestBody Payment payment) {

        if (payment.getRideId() == null
                || payment.getRideId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (payment.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (payment.getPaymentMethod() == null
                || payment.getPaymentMethod().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Payment savedPayment =
                paymentService.createPaymentRecord(payment);

        return ResponseEntity.ok(savedPayment);
    }

    // READ - Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );
    }

    // READ - Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable String id,
            @RequestBody Payment payment) {

        if (payment.getRideId() == null
                || payment.getRideId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (payment.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (payment.getPaymentMethod() == null
                || payment.getPaymentMethod().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                paymentService.updatePayment(id, payment)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable String id) {

        paymentService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }
}