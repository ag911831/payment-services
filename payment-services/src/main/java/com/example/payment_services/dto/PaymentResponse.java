package com.example.payment_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentResponse{
    private String orderId;
    private BigDecimal amount;
    private String gatewayOrderId;
    private String gateway;
}
