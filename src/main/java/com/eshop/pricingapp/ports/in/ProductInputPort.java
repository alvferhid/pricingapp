package com.eshop.pricingapp.ports.in;

import com.eshop.pricingapp.domain.model.Product;

import java.util.List;

public interface ProductInputPort {

    List<Product> getAllProductPrices();
}
