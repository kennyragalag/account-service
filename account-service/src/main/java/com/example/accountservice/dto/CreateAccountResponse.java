package com.example.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccountResponse {

    private final Long customerNumber;
    private final int transactionStatusCode;
    private final String transactionStatusDescription;

    public static CreateAccountResponse created(Long customerNumber) {
        return new CreateAccountResponse(customerNumber,
                ResponseStatus.CREATED_CODE, ResponseStatus.CREATED_DESC);
    }
}
