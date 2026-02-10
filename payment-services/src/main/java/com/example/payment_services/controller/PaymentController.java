package com.example.payment_services.controller;

import com.example.payment_services.dto.PaymentRequest;
import com.example.payment_services.dto.PaymentResponse;
import com.example.payment_services.dto.PaymentTransactionDto;
import com.example.payment_services.entity.PaymentTransaction;
import com.example.payment_services.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @GetMapping("/test")
    public String validationCheck(){
        return "working on port 8080";
    }
    @PostMapping("/initiate")
    public PaymentResponse initiate(@RequestBody PaymentRequest req) throws RazorpayException {
        return paymentService.initiatePayment(req);
    }
    @GetMapping("/public/getTransaction")
    public List<PaymentTransactionDto> getTransactionByUserId(@RequestParam Long userId){
        return paymentService.getAllPaymentTransactionByUserId(userId);

    }
}
