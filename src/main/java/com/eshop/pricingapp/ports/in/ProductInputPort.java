package com.eshop.pricingapp.ports.in;

import com.eshop.pricingapp.domain.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductInputPort {

    List<Product> getAllProducts();

    Optional<Product> findProductPriceByDate(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
