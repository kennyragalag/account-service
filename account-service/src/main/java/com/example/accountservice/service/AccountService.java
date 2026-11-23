package com.example.accountservice.service;

import com.example.accountservice.dto.*;
import com.example.accountservice.entity.*;
import com.example.accountservice.exception.CustomerNotFoundException;
import com.example.accountservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final CustomerRepository customerRepository;

    public AccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Long createAccount(CreateAccountRequest request) {
        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerMobile(request.getCustomerMobile());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setAddress1(request.getAddress1());
        customer.setAddress2(request.getAddress2());

        Account account = new Account();
        account.setAccountType(request.getAccountType());
        account.setAvailableBalance(BigDecimal.ZERO);
        customer.addAccount(account);

        return customerRepository.save(customer).getCustomerNumber();
    }

    @Transactional(readOnly = true)
    public CustomerInquiryResponse getCustomer(Long customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(ResponseStatus.NOT_FOUND_DESC));

        return CustomerInquiryResponse.builder()
                .customerNumber(String.valueOf(customer.getCustomerNumber()))
                .customerName(customer.getCustomerName())
                .customerMobile(customer.getCustomerMobile())
                .customerEmail(customer.getCustomerEmail())
                .address1(customer.getAddress1())
                .address2(customer.getAddress2())
                .savings(accountsOfType(customer, AccountType.SAVINGS))
                .checking(accountsOfType(customer, AccountType.CHECKING))
                .transactionStatusCode(ResponseStatus.FOUND_CODE)
                .transactionStatusDescription(ResponseStatus.FOUND_DESC)
                .build();
    }

    private List<AccountInfo> accountsOfType(Customer customer, AccountType type) {
        return customer.getAccounts().stream()
                .filter(account -> account.getAccountType() == type)
                .map(account -> new AccountInfo(account.getAccountNumber(),
                        type.getDescription(), account.getAvailableBalance()))
                .collect(Collectors.toList());
    }
}

