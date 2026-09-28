package com.example.payment_application.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.payment_application.entities.Account;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;

@Repository 
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Lock (LockModeType.PESSIMISTIC_WRITE)
    @Query ("SELECT a FROM ACCOUNT a WHERE a.ownerId = :ownerId")
    Optional<Account> findByOwnerIdForUpdate(@Param("ownerId") UUID ownerId);

    Optional<Account> findByOwnerId(UUID ownerId);
}