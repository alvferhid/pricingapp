package com.eshop.pricingapp.domain.mapper;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductInputMapper {

    ProductDto toDto(Product product);

    List<ProductDto> toDto(List<Product> products);
}
