package com.ide.realms.IdieRealms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountAlreadyExists extends RuntimeException {

    public AccountAlreadyExists(String message) {
        super(message);
    }
}