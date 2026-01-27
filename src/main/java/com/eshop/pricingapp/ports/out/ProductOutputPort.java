package com.eshop.pricingapp.ports.out;

import com.eshop.pricingapp.domain.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductOutputPort {

    List<Product> getAllProducts();

    List<Product> findProductsByDate(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
