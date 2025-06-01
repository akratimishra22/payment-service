package com.gharelu.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private Long orderId;
    private Long productId;
    private int quantity;
    private Double amount;
    private String status;  // Should be "CONFIRMED"
}

