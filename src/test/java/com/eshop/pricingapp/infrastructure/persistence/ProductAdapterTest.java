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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

    @DisplayName("When retrieving price by date product and brand returns the one with highest priority")
    @Test
    void whenRetrievingPriceByDateProductAndBrandReturnsTheOneWithHighestPriority() {
        //GIVEN
        ProductEntity entityWithHigherPriority = generateEntity(1);
        Optional<ProductEntity> optionalEntity = Optional.ofNullable(entityWithHigherPriority);
        Product modelWithHigherPriority = generateModel(2);

        LocalDateTime date = LocalDateTime.of(2022, 10, 10, 10, 10, 10);
        Integer productId = 1;
        Integer brandId = 1;

        //WHEN
        when(productRepository.findProductPriceByDate(date, productId, brandId)).thenReturn(optionalEntity);
        when(mapper.toModel(entityWithHigherPriority)).thenReturn(modelWithHigherPriority);

        //THEN
        Optional<Product> resultOptional = adapter.findProductPriceByDate(date, productId, brandId);
        assertThat(resultOptional.isPresent()).isTrue();
        Product result = resultOptional.get();
        assertThat(result.getPriority()).isEqualTo(2);
    }


    @DisplayName("Throws internal server error when parameters are invalid")
    @Test
    void throwsInternalServerErrorWhenParametersAreInvalid() {
        // GIVEN
        LocalDateTime applicationDate = null;
        Integer productId = null;
        Integer brandId = null;

        // WHEN & THEN
        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> adapter.findProductPriceByDate(applicationDate, productId, brandId))
                .withMessageContaining("Null values")
                .extracting(ResponseStatusException::getStatusCode).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @DisplayName("Throws bad request error when brandId or productId are below 1")
    @Test
    void throwsBadRequestErrorWhenBrandIdOrProductIdAreBelow1() {
        // GIVEN
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer invalidProductId = 0;
        Integer invalidBrandId = 0;

        // WHEN & THEN
        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> adapter.findProductPriceByDate(applicationDate, invalidProductId, invalidBrandId))
                .withMessageContaining("Brand Id and product ID valuest must be above 0")
                .extracting(ResponseStatusException::getStatusCode).isEqualTo(BAD_REQUEST);
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