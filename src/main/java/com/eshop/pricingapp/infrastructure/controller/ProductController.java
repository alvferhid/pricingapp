package com.eshop.pricingapp.infrastructure.controller;

import com.eshop.pricingapp.domain.mapper.ProductInputMapper;
import com.eshop.pricingapp.infrastructure.dto.ProductDto;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
