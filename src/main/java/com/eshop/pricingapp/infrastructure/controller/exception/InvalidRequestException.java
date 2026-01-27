package com.eshop.pricingapp.infrastructure.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRequestException extends ResponseStatusException {
    public InvalidRequestException(HttpStatus status, String reason) {
        super(status, reason);
    }
}

