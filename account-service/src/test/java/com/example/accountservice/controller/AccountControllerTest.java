package com.example.accountservice.controller;

import com.example.accountservice.dto.*;
import com.example.accountservice.exception.CustomerNotFoundException;
import com.example.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private static final String URL = "/api/v1/account";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private static String payload(String email, String accountType) {
        return "{\"customerName\":\"Test\",\"customerMobile\":\"09081234567\","
                + (email == null ? "" : "\"customerEmail\":\"" + email + "\",")
                + "\"address1\":\"test\",\"address2\":\"test\""
                + (accountType == null ? "" : ",\"accountType\":\"" + accountType + "\"")
                + "}";
    }

    @Test
    void create_returns201() throws Exception {
        when(accountService.createAccount(any())).thenReturn(12345678L);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(payload("test12345@gmail.com", "S")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerNumber").value(12345678))
                .andExpect(jsonPath("$.transactionStatusCode").value(201))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer account created"));
    }

    @Test
    void create_missingEmail_returns400() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(payload(null, "S")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Email is required field"));
    }

    @Test
    void create_invalidEmail_returns400() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(payload("not-an-email", "S")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusDescription").value("Email is invalid"));
    }

    @Test
    void create_missingAccountType_returns400() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(payload("test12345@gmail.com", null)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusDescription").value("Account type is required field"));
    }

    @Test
    void create_invalidAccountType_returns400() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(payload("test12345@gmail.com", "X")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Account type must be S (Savings) or C (Checking)"));
    }

    @Test
    void inquiry_returns302() throws Exception {
        CustomerInquiryResponse response = CustomerInquiryResponse.builder()
                .customerNumber("12345678")
                .customerName("Test")
                .customerMobile("09081234567")
                .customerEmail("test12345@gmail.com")
                .address1("test")
                .address2("test")
                .savings(Collections.singletonList(new AccountInfo(10001L, "Savings", new BigDecimal("500"))))
                .checking(Collections.emptyList())
                .transactionStatusCode(302)
                .transactionStatusDescription("Customer Account found")
                .build();
        when(accountService.getCustomer(12345678L)).thenReturn(response);

        mockMvc.perform(get(URL + "/12345678"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.customerNumber").value("12345678"))
                .andExpect(jsonPath("$.savings[0].accountNumber").value(10001))
                .andExpect(jsonPath("$.savings[0].accountType").value("Savings"))
                .andExpect(jsonPath("$.savings[0].availableBalance").value(500))
                .andExpect(jsonPath("$.checking").doesNotExist())
                .andExpect(jsonPath("$.transactionStatusCode").value(302));
    }

    @Test
    void inquiry_unknownCustomer_returns401() throws Exception {
        when(accountService.getCustomer(99L)).thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get(URL + "/99"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.transactionStatusCode").value(401))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer not found"));
    }

    @Test
    void inquiry_nonNumericCustomerNumber_returns400() throws Exception {
        mockMvc.perform(get(URL + "/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400));
    }
}
