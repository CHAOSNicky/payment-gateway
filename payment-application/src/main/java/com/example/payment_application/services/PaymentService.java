package com.example.payment_application.services;

import java.time.Duration;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.payment_application.dtos.PaymentRequestDto;
import com.example.payment_application.dtos.PaymentResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Slf4j 
public class PaymentService {

    private final IdempotencyService idempotencyService;
    private final PaymentTransactionService paymentTx;

    public PaymentResponseDto initiatePayment(String idempotencyKey, PaymentRequestDto request){
        Optional<PaymentResponseDto> existing = paymentTx.findExistingOrder(idempotencyKey);
        if(existing.isPresent()){
            return existing.get();
        }

        boolean locked = idempotencyService.acquireLock(idempotencyKey, Duration.ofSeconds(5));
        if(!locked){
            throw new IllegalStateException("Payment request is currently being processed");
        }

        try{

            return paymentTx.createOrderInTransaction(idempotencyKey, request);
        } finally{
            idempotencyService.releaseLock(idempotencyKey);
        }
    }
}