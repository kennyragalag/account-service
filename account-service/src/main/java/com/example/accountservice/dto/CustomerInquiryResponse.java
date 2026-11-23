package com.example.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter 
@Builder 
public class CustomerInquiryResponse {

    private final String customerNumber;
    private final String customerName;
    private final String customerMobile;
    private final String customerEmail;
    private final String address1;
    private final String address2;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<AccountInfo> savings;

    @JsonInclude (JsonInclude.Include.NON_EMPTY)
    private final List<AccountInfo> checking;

    private final int transactionStatusCode;
    private final String transactionStatusDescription;  

}
