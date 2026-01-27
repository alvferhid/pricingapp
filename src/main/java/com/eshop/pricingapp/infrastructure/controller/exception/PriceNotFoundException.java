package com.eshop.pricingapp.infrastructure.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public class PriceNotFoundException extends ResponseStatusException {

    public PriceNotFoundException(HttpStatus status, Integer productId, Integer brandId, LocalDateTime applicationDate) {
        super(
                status,
                """
                        No price found for product %d
                        and brand %d
                        at %s
                        """.formatted(productId, brandId, applicationDate)
        );
    }
}


