package com.gharelu.payment_service.controller;

import com.gharelu.payment_service.model.Payment;
import com.gharelu.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    @Autowired
    @Qualifier("authServiceWebClient")
    private final WebClient webClient;

    private String isTokenValid(String authHeader) {
        String authResponse = webClient.get()
                .header("Authorization", authHeader)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return authResponse;
    }
    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                               @RequestBody Payment payment) {
        String authResponse = isTokenValid(authHeader);
        if(authResponse.equals("Valid"))
            return ResponseEntity.ok(service.processPayment(payment));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                     @PathVariable Long orderId) {
        String authResponse = isTokenValid(authHeader);
        if(authResponse.equals("Valid"))
            return service.getPaymentByOrderId(orderId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String authResponse = isTokenValid(authHeader);
        if(authResponse.equals("Valid"))
            return ResponseEntity.ok(service.getAllPayments());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
