package com.sujal.springboottemplate.dto;

import com.sujal.springboottemplate.entity.Account;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(
        Long id,
        String holderName,
        BigDecimal balance,
        String currency,
        Long version,
        Instant createdAt,
        Instant updatedAt
) {
    public static AccountResponse from(Account a) {
        return new AccountResponse(
                a.getId(), a.getHolderName(), a.getBalance(), a.getCurrency(),
                a.getVersion(), a.getCreatedAt(), a.getUpdatedAt());
    }
}