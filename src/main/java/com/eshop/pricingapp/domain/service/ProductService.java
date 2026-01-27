package com.eshop.pricingapp.domain.service;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import com.eshop.pricingapp.ports.out.ProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInputPort {

    private final ProductOutputPort productOutputPort;

    @Override
    public List<Product> getAllProducts() {
        return productOutputPort.getAllProducts();
    }

    @Override
    public Optional<Product> findProductPriceByDate(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return productOutputPort.findProductsByDate(applicationDate, productId, brandId).stream()
                .max(Comparator.comparingInt(Product::getPriority));
    }

}
