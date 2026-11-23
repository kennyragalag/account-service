package com.example.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccountInfo {

    private final Long accountNumber;
    private final String accountType;
    private final BigDecimal availableBalance;
}

