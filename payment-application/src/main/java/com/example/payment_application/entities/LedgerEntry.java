package com.example.payment_application.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.payment_application.enums.EntryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "ledger_entries")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class LedgerEntry {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false)
    private UUID transactionId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "account_id", nullable = false)
    private Account account;
    
    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private EntryType entryType;

    @Column (nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @CreationTimestamp 
    @Column (nullable = false)
    private LocalDateTime createdAt;
}