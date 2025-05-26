package com.gharelu.payment_service.service;

import com.gharelu.payment_service.model.Payment;
import com.gharelu.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment processPayment(Payment payment) {
        // Defaulting status to SUCCESS for this mock version
        payment.setPaymentStatus("SUCCESS");
        return repository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

}

