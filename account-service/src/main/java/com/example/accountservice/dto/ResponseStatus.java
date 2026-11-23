package com.example.accountservice.dto;

/** Status codes and descriptions returned in the transaction status fields. */
public final class ResponseStatus {

    public static final int CREATED_CODE = 201;
    public static final String CREATED_DESC = "Customer account created";

    public static final int FOUND_CODE = 302;
    public static final String FOUND_DESC = "Customer Account found";

    public static final int BAD_REQUEST_CODE = 400;

    /** The specification defines 401 as the "customer not found" status. */
    public static final int NOT_FOUND_CODE = 401;
    public static final String NOT_FOUND_DESC = "Customer not found";

    public static final int INTERNAL_ERROR_CODE = 500;
    public static final String INTERNAL_ERROR_DESC = "Unexpected error occurred";

    private ResponseStatus() {
    }
}
