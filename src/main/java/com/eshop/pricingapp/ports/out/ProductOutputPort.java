package com.eshop.pricingapp.ports.out;

import com.eshop.pricingapp.domain.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductOutputPort {

    List<Product> getAllProductPrices();

    Optional<Product> findProductPriceByDate(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
