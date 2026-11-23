package com.example.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int transactionStatusCode;
    private final String transactionStatusDescription;
}
