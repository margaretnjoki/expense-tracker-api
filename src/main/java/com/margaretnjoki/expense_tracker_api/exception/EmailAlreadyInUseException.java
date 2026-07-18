package com.margaretnjoki.expense_tracker_api.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email already registered: " + email);
    }

}
