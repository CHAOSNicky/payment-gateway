package com.example.payment_application.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment_application.entities.LedgerEntry;

@Repository 
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, UUID> {

}