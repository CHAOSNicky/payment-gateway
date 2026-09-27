package com.example.payment_application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.payment_application.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class PaymentResponseDto {
    private UUID orderId;
    private UUID merchantId;
    private UUID customerId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String idempotencyKey;
    private LocalDateTime createdAt;
}