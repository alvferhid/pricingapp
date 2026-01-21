package com.eshop.pricingapp.domain.service;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.persistence.mapper.ProductOutputMapper;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import com.eshop.pricingapp.ports.out.ProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInputPort {

    private final ProductOutputPort productOutputPort;
    private final ProductOutputMapper productOutputMapper;

    @Override
    public List<Product> getAllProductPrices() {
        return productOutputPort.getAllProductPrices();
    }

    @Override
    public Optional<Product> findProductPriceByDate(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return productOutputPort.findProductPriceByDate(applicationDate, productId, brandId);
    }

}
