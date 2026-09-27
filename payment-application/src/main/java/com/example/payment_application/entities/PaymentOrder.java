package com.example.payment_application.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.payment_application.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "payment_orders", indexes = {
    @Index(name = "idx_order_merchant", columnList = "merchant_id"),
    @Index(name = "idx_order_status", columnList = "status")
})
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class PaymentOrder {
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false)
    private UUID merchantId;

    @Column (nullable = false)
    private UUID customerId;

    @Column (nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column (nullable = false, length = 3)
    private String currency;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private PaymentStatus status;

    @Column (nullable = false)
    private String paymentMethod;

    @Column (unique = true)
    private String idempotencyKey;

    private String failureReason;

    @CreationTimestamp 
    @Column (updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp 
    private LocalDateTime updatedAt;
}
