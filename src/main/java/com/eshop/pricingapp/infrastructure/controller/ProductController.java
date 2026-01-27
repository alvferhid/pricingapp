package com.eshop.pricingapp.infrastructure.controller;

import com.eshop.pricingapp.domain.mapper.ProductInputMapper;
import com.eshop.pricingapp.infrastructure.controller.exception.InvalidRequestException;
import com.eshop.pricingapp.infrastructure.controller.exception.PriceNotFoundException;
import com.eshop.pricingapp.infrastructure.dto.ProductDto;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductInputPort productInputPort;
    private final ProductInputMapper productInputMapper;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok((productInputMapper.toDto(productInputPort.getAllProducts())));
    }

    @GetMapping("/offer/{applicationDate}/{productId}/{brandId}")
    public ResponseEntity<ProductDto> findProductPriceByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                                             @PathVariable Integer productId, @PathVariable Integer brandId) {
        log.info("Attempting to search price for product {}, brand {}, date {}",
                productId, brandId, applicationDate);

        checkFields(productId, brandId);

        return productInputPort
                .findProductPriceByDate(applicationDate, productId, brandId)
                .map(product -> ResponseEntity.ok(productInputMapper.toDto(product)))
                .orElseThrow(() ->
                        new PriceNotFoundException(HttpStatus.NOT_FOUND, productId, brandId, applicationDate)
                );
    }

    private static void checkFields(Integer productId, Integer brandId) {

        if (productId == null || brandId == null) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "parameters can't be null");
        }

        if (productId < 1) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "productId must be greater than 0");
        }

        if (brandId < 1) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "brandId must be greater than 0");
        }
    }
}
