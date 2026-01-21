package com.eshop.pricingapp.infrastructure.controller;

import com.eshop.pricingapp.domain.mapper.ProductInputMapper;
import com.eshop.pricingapp.infrastructure.dto.ProductDto;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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
    public List<ProductDto> getAllProductPrices() {
        return productInputMapper.toDto(productInputPort.getAllProductPrices());
    }

    @GetMapping("/offer/{applicationDate}/{productId}/{brandId}")
    public ProductDto findProductPriceByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                             @PathVariable Integer productId, @PathVariable Integer brandId) {
        log.info("Attempting to search price for product {}, with brand id {}, at date {}", productId, brandId, applicationDate);
        return productInputPort.findProductPriceByDate(applicationDate, productId, brandId)
                .map(product -> productInputMapper.toDto(product))
                .orElseGet(() -> null);
    }
}
