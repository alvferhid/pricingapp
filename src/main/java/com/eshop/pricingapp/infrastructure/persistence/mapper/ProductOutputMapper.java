package com.eshop.pricingapp.infrastructure.persistence.mapper;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductOutputMapper {

    Product toModel(ProductEntity productEntity);

    List<Product> toModel(List<ProductEntity> productEntities);
}
