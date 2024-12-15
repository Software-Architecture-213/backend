package com.example.brandservice.exception;


import java.io.Serial;

public class TokenVerificationException extends AppException {

    @Serial
    private static final long serialVersionUID = -7244519491059365888L;

    private static final String DEFAULT_MESSAGE = "Authentication failure: Token missing, invalid or expired";

    public TokenVerificationException() {
        super(ErrorCode.TOKEN_VERIFICATION_FAILURE);
    }

}