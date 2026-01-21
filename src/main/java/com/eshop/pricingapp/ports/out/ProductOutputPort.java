package com.eshop.pricingapp.ports.out;

import com.eshop.pricingapp.domain.model.Product;

import java.util.List;

public interface ProductOutputPort {

    List<Product> getAllProductPrices();
}
