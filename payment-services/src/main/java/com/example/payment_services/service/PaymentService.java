package com.example.payment_services.service;

import com.example.payment_services.dto.PaymentRequest;
import com.example.payment_services.dto.PaymentResponse;
import com.example.payment_services.dto.PaymentTransactionDto;
import com.example.payment_services.entity.PaymentTransaction;
import com.example.payment_services.repository.PaymentTransactionRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentTransactionRepository repo;

    @Autowired
    private RazorpayClient razorpayClient;

    public PaymentResponse initiatePayment(PaymentRequest req) throws RazorpayException {

        Optional<PaymentTransaction> existing=
                repo.findByIdempotencyKey(req.getIdempotencyKey());

        if(existing.isPresent()){
            return mapToResponse(existing.get());
        }

        PaymentTransaction tx=new PaymentTransaction();

        tx.setOrderId(req.getOrderId());
        tx.setAmount(req.getAmount());
        tx.setCurrency("INR");
        tx.setStatus("PENDING");
        tx.setGateway("RAZORPAY");
        tx.setIdempotencyKey(req.getIdempotencyKey());
        tx.setUserId(req.getUserId());
        repo.save(tx);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("amount",req.getAmount().multiply(BigDecimal.valueOf(100)));
        jsonObject.put("currency","INR");
        jsonObject.put("receipt",tx.getOrderId());

        Order order =razorpayClient.orders.create(jsonObject);

        //Save gateway order id

        tx.setGatewayOrderId(order.get("id"));
        repo.save(tx);

        return mapToResponse(tx);



    }

    private PaymentResponse mapToResponse(PaymentTransaction tx) {
        return new PaymentResponse(
                tx.getOrderId(),
                tx.getAmount(),
                tx.getGatewayOrderId(),
                "RAZORPAY"
        );
    }
    public List<PaymentTransactionDto> getAllPaymentTransactionByUserId(Long userId){

        List<PaymentTransaction> paymentTransactionList = repo.findByUserId(userId);
        if(paymentTransactionList!= null && !paymentTransactionList.isEmpty()){
            List<PaymentTransactionDto> paymentTransactionDtoList = new ArrayList<>();
            for(PaymentTransaction paymentTransaction :paymentTransactionList){
                PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto(paymentTransaction.getOrderId(),
                        paymentTransaction.getAmount(),paymentTransaction.getCurrency(),paymentTransaction.getStatus(),paymentTransaction.getGatewayPaymentId(),paymentTransaction.getGatewayPaymentId(),paymentTransaction.getGatewayOrderId(),paymentTransaction.getIdempotencyKey(),paymentTransaction.getUserId(),
                        paymentTransaction.getCreatedAt(),paymentTransaction.getUpdatedAt());

                paymentTransactionDtoList.add(paymentTransactionDto);

            }
            return paymentTransactionDtoList;
        }

        return null;
    }

}
