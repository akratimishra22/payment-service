package com.gharelu.payment_service.service;

import com.gharelu.payment_service.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment processPayment(Payment payment);
    Optional<Payment> getPaymentByOrderId(Long orderId);
    List<Payment> getAllPayments();
}

