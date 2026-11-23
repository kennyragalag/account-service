package com.example.accountservice.controller;

import com.example.accountservice.dto.*;
import com.example.accountservice.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<CreateAccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Long customerNumber = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateAccountResponse.created(customerNumber));
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<CustomerInquiryResponse> getCustomer(@PathVariable Long customerNumber) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(accountService.getCustomer(customerNumber));
    }
}