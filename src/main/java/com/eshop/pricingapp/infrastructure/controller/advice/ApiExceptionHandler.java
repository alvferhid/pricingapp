package com.eshop.pricingapp.infrastructure.controller.advice;


import com.eshop.pricingapp.infrastructure.controller.exception.InvalidRequestException;
import com.eshop.pricingapp.infrastructure.controller.exception.PriceNotFoundException;
import com.eshop.pricingapp.infrastructure.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(InvalidRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError("INVALID_INPUT", ex.getMessage()));
    }

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ApiError> handlePriceNotFound(PriceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError("PRICE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch() {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        "INVALID_DATE_FORMAT",
                        "applicationDate must follow ISO-8601 format"
                ));
    }
}
