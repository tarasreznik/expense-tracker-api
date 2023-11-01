package com.example.ExpanseTrackerAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ItemAlreadyExistsException extends RuntimeException{
    private static final long serialVersionId = 1L;

    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
