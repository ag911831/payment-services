package com.example.payment_services.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransactionDto {
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String gateway;
    private String gatewayPaymentId;
    private String gatewayOrderId;
    private String idempotencyKey;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
