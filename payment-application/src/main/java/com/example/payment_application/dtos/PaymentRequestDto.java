package com.example.payment_application.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class PaymentRequestDto {
    @NotNull (message = "merchantId is required")
    private UUID merchantId;

    @NotNull (message = "customerId is required")
    private UUID customerId;

    @NotNull (message = "amount is required")
    @DecimalMin (value = "0.01", message = "amount must be greater than 0")
    private BigDecimal amount;
  
    @NotBlank (message = "currency is required")
    private String currency;

    @NotBlank (message = "paymentMethod is required")
    private String paymentMethod;
}