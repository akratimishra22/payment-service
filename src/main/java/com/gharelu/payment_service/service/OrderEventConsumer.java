package com.gharelu.payment_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gharelu.payment_service.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created-topic", groupId = "1", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderEvent(String message) {
        try {
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
            log.info("Received Order Event: {}", event);

            // Simulate payment processing logic
            processPayment(event);
        } catch (Exception e) {
            log.error("Error while processing order event", e);
        }
    }

    private void processPayment(OrderEvent event) {
        // TODO: Add payment gateway integration or business logic
        log.info("Processing payment for Order ID: {}, Amount: {}", event.getOrderId(), event.getAmount());
    }
}

