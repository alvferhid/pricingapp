package com.eshop.pricingapp.infrastructure.persistence;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.persistence.entity.ProductEntity;
import com.eshop.pricingapp.infrastructure.persistence.mapper.ProductOutputMapper;
import com.eshop.pricingapp.infrastructure.persistence.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductAdapterTest {


    @Mock
    ProductRepository productRepository;

    @Mock
    ProductOutputMapper mapper;

    @InjectMocks
    ProductAdapter adapter;

    @DisplayName("Retrieves all products from repository")
    @Test
    void retrievesAllProductsFromRepository() {
        //GIVEN
        List<ProductEntity> entities = List.of(generateEntity(1));
        List<Product> models = List.of(generateModel(1));

        //WHEN
        when(productRepository.findAllProducts()).thenReturn(entities);
        when(mapper.toModel(entities)).thenReturn(models);

        //THEN
        List<Product> result = adapter.getAllProducts();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPrice()).isEqualTo(3.50);
    }

    @DisplayName("Retrieves products by date")
    @Test
    void retrievesProductsByDate() {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2022, 10, 10, 10, 10, 10);
        Integer productId = 1;
        Integer brandId = 1;
        List<ProductEntity> entities = List.of(generateEntity(1));
        List<Product> models = List.of(generateModel(1));

        //WHEN
        when(productRepository.findproductsByDate(date, productId, brandId)).thenReturn(entities);
        when(mapper.toModel(entities)).thenReturn(models);

        //THEN
        List<Product> result = adapter.findProductsByDate(date, productId, brandId);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPrice()).isEqualTo(3.50);
    }

    Product generateModel(Integer priority) {
        return Product.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2022, 10, 02, 10, 10, 10))
                .endDate(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .priceList(1)
                .productId(1)
                .price(3.50)
                .priority(priority)
                .currency("euro")
                .build();
    }

    ProductEntity generateEntity(Integer priority) {
        return ProductEntity.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2022, 10, 02, 10, 10, 10))
                .endDate(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .priceList(1)
                .productId(1)
                .price(3.50)
                .priority(priority)
                .currency("euro")
                .build();
    }
}