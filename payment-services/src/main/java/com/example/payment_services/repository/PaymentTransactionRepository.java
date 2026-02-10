package com.example.payment_services.repository;

import com.example.payment_services.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction,Long> {
    Optional<PaymentTransaction> findByIdempotencyKey(String key);

    Optional<PaymentTransaction> findByGatewayPaymentId(String paymentId);

    List<PaymentTransaction> findByUserId(Long userId);


}
