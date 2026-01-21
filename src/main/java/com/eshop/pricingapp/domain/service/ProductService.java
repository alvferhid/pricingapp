package com.eshop.pricingapp.domain.service;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.persistence.mapper.ProductOutputMapper;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import com.eshop.pricingapp.ports.out.ProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInputPort {

    private final ProductOutputPort productOutputPort;
    private final ProductOutputMapper productOutputMapper;

    @Override
    public List<Product> getAllProductPrices() {
        return productOutputPort.getAllProductPrices();
    }
}
