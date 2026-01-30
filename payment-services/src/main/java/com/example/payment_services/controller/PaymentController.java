package com.example.payment_services.controller;

import com.example.payment_services.dto.PaymentRequest;
import com.example.payment_services.dto.PaymentResponse;
import com.example.payment_services.entity.PaymentTransaction;
import com.example.payment_services.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public PaymentResponse initiate(@RequestBody PaymentRequest req) throws RazorpayException {
        return paymentService.initiatePayment(req);
    }
}
