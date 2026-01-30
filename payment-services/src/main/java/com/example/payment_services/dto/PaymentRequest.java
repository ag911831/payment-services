package com.example.payment_services.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    String orderId;
    BigDecimal amount;
    String idempotencyKey;

}
