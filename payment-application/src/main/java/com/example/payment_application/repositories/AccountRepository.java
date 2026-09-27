package com.example.payment_application.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment_application.entities.Account;

@Repository 
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByOwnerId(UUID ownerId);
}