package com.ridelink.farepayment.service;

import com.ridelink.farepayment.exception.PaymentNotFoundException;
import com.ridelink.farepayment.model.Payment;
import com.ridelink.farepayment.model.Receipt;
import com.ridelink.farepayment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // =========================
    // EXISTING PAYMENT CREATE
    // =========================

    public Payment createPayment(
            String rideId,
            double amount,
            String paymentMethod) {

        Payment payment = new Payment();

        payment.setRideId(rideId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now().toString());

        return paymentRepository.save(payment);
    }

    // =========================
    // EXISTING STATUS UPDATE
    // =========================

    public Payment updatePaymentStatus(
            String paymentId,
            String status) {

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));

        payment.setStatus(status);

        return paymentRepository.save(payment);
    }

    // =========================
    // EXISTING GET PAYMENT
    // =========================

    public Payment getPayment(String paymentId) {

        return paymentRepository
                .findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));
    }

    // =========================
    // EXISTING RECEIPT
    // =========================

    public Receipt generateReceipt(String paymentId) {

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));

        Receipt receipt = new Receipt();

        receipt.setReceiptId("REC-" + payment.getId());
        receipt.setPaymentId(payment.getId());
        receipt.setRideId(payment.getRideId());
        receipt.setAmount(payment.getAmount());
        receipt.setPaymentMethod(payment.getPaymentMethod());
        receipt.setPaymentStatus(payment.getStatus());
        receipt.setPaymentDate(payment.getPaymentDate());

        return receipt;
    }

    // =========================
    // CRUD OPERATIONS
    // =========================

    // CREATE
    public Payment createPaymentRecord(Payment payment) {

        return paymentRepository.save(payment);
    }

    // READ - Get all payments
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    // READ - Get payment by ID
    public Payment getPaymentById(String id) {

        return paymentRepository
                .findById(id)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found"));
    }

    // UPDATE
    public Payment updatePayment(
            String id,
            Payment updatedPayment) {

        Payment existingPayment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new PaymentNotFoundException(
                                        "Payment not found"));

        existingPayment.setRideId(
                updatedPayment.getRideId());

        existingPayment.setAmount(
                updatedPayment.getAmount());

        existingPayment.setPaymentMethod(
                updatedPayment.getPaymentMethod());

        existingPayment.setStatus(
                updatedPayment.getStatus());

        existingPayment.setPaymentDate(
                updatedPayment.getPaymentDate());

        return paymentRepository.save(existingPayment);
    }

    // DELETE
    public void deletePayment(String id) {

        if (!paymentRepository.existsById(id)) {

            throw new PaymentNotFoundException(
                    "Payment not found");
        }

        paymentRepository.deleteById(id);
    }
}