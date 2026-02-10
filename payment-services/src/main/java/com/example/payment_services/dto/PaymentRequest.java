package com.example.payment_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentRequest {

    String orderId;
    BigDecimal amount;
    String idempotencyKey;
    Long userId;
}
