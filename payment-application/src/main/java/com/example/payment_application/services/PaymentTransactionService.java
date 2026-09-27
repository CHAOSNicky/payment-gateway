package com.example.payment_application.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.payment_application.dtos.PaymentRequestDto;
import com.example.payment_application.dtos.PaymentResponseDto;
import com.example.payment_application.entities.PaymentOrder;
import com.example.payment_application.repositories.PaymentOrderRepository;
import com.example.payment_application.enums.PaymentStatus;

@Service 
@RequiredArgsConstructor 
@Slf4j 
public class PaymentTransactionService {
    
    private final PaymentOrderRepository paymentOrderRepository;

    @Transactional (readOnly = true)
    public Optional<PaymentResponseDto> findExistingOrder(String idempotencyKey){
        return paymentOrderRepository.findByIdempotencyKey(idempotencyKey)
            .map(this::toResponseDto);
    }

    @Transactional 
    public PaymentResponseDto createOrderInTransaction(String idempotencyKey, PaymentRequestDto request){
        Optional<PaymentOrder> existing = paymentOrderRepository.findByIdempotencyKey(idempotencyKey);
        if(existing.isPresent()){
            return toResponseDto(existing.get());
        }

        PaymentOrder order = PaymentOrder.builder()
            .merchantId(request.getMerchantId())
            .customerId(request.getCustomerId())
            .amount(request.getAmount())
            .currency(request.getCurrency())
            .paymentMethod(request.getPaymentMethod())
            .status(PaymentStatus.INITIATED)
            .idempotencyKey(idempotencyKey)
            .build();

        PaymentOrder saved = paymentOrderRepository.save(order);
        log.info("Saved and Commited Order", saved.getId());
        return toResponseDto(saved);
    }

    private PaymentResponseDto toResponseDto(PaymentOrder order){
        return PaymentResponseDto.builder()
        .orderId(order.getId())
        .merchantId(order.getMerchantId())
        .customerId(order.getCustomerId())
        .amount(order.getAmount())
        .currency(order.getCurrency())
        .status(order.getStatus())
        .idempotencyKey(order.getIdempotencyKey())
        .createdAt(order.getCreatedAt())
        .build();
    }
}