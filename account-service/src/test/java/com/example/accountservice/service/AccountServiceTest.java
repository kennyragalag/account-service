package com.example.accountservice.service;

import com.example.accountservice.dto.*;
import com.example.accountservice.entity.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** End-to-end check of service + JPA + H2. */
@SpringBootTest
class AccountServiceApplicationTest {

    @Autowired
    private AccountService accountService;

    @Test
    void createThenInquire() {
        Long number = accountService.createAccount(CreateAccountRequest.builder()
                .customerName("Test").customerMobile("09081234567")
                .customerEmail("test12345@gmail.com").address1("test")
                .accountType(AccountType.CHECKING).build());

        CustomerInquiryResponse response = accountService.getCustomer(number);

        assertEquals(String.valueOf(number), response.getCustomerNumber());
        assertEquals(1, response.getChecking().size());
        assertTrue(response.getSavings().isEmpty());
        assertEquals(0, response.getChecking().get(0).getAvailableBalance().signum());
    }
}
