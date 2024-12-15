package com.example.brandservice.exception;

import lombok.NonNull;

import java.io.Serial;
//ResponseStatusException
public class AccountAlreadyExistsException extends AppException {

    @Serial
    private static final long serialVersionUID = 7439642984069939024L;

    public AccountAlreadyExistsException(@NonNull final String reason) {
        super(ErrorCode.ACCOUNT_ALREADY_EXISTS);
    }

}