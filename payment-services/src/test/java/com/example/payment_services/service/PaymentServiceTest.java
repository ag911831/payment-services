package com.example.payment_services.service;

import com.example.payment_services.dto.PaymentRequest;
import com.example.payment_services.dto.PaymentResponse;
import com.example.payment_services.entity.PaymentTransaction;
import com.example.payment_services.repository.PaymentTransactionRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private PaymentTransactionRepository repository;

    @Mock
    private RazorpayClient razorpayClient;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void initiatePayment_shouldReturnExistingTransaction_whenIdempotencyKeyExists()
            throws RazorpayException {

        BigDecimal amount = new BigDecimal("5");

        PaymentRequest paymentRequest =
                new PaymentRequest("orderId", amount, "idempotencyKey");

        PaymentTransaction tx = new PaymentTransaction();
        tx.setOrderId("orderId");
        tx.setAmount(amount);
        tx.setGatewayOrderId("order_existing");
        tx.setCurrency("INR");
        tx.setStatus("PENDING");
        tx.setGateway("RAZORPAY");
        tx.setIdempotencyKey("idempotencyKey");

        Mockito.when(repository.findByIdempotencyKey("idempotencyKey"))
                .thenReturn(Optional.of(tx));

        PaymentResponse response =
                paymentService.initiatePayment(paymentRequest);

        assertNotNull(response);
        assertEquals("order_existing", response.getGatewayOrderId());
        assertEquals(amount, response.getAmount());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
        Mockito.verifyNoInteractions(razorpayClient);
    }
}
