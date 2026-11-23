package com.example.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AccountType {

    SAVINGS("S", "Savings"),
    CHECKING("C", "Checking");

    private final String code;
    private final String description;

    AccountType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AccountType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account type must be S (Savings) or C (Checking)"));
    }
}
