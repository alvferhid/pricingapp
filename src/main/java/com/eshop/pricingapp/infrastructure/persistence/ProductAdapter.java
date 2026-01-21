package com.eshop.pricingapp.infrastructure.persistence;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.persistence.mapper.ProductOutputMapper;
import com.eshop.pricingapp.infrastructure.persistence.repository.ProductRepository;
import com.eshop.pricingapp.ports.out.ProductOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAdapter implements ProductOutputPort {

    private final ProductOutputMapper productOutputMapper;
    private final ProductRepository productRepository;

    public List<Product> getAllProductPrices() {
        return productOutputMapper.toModel(productRepository.findAllProductPrices());
    }
}
