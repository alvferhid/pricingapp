package com.eshop.pricingapp.domain.service;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.ports.out.ProductOutputPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductOutputPort productOutputPort;

    @InjectMocks
    ProductService service;

    @DisplayName("When getting all products returns lists of products")
    @Test
    void whenGettingAllProductsReturnsListProducts() {
        //GIVEN
        List<Product> products = List.of(generateModel(), generateModel());

        //WHEN
        when(productOutputPort.getAllProducts()).thenReturn(products);

        //THEN
        List<Product> result = service.getAllProducts();
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("When finding product by date productid and brandId returns optional")
    @Test
    void whenFindingProductByDateProductIdAndBrandIdReturnsOptional() {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2022, 10, 10, 10, 10, 10);
        Integer productId = 1;
        Integer brandId = 1;
        Product product = generateModel();

        //WHEN
        when(productOutputPort.findProductsByDate(date, productId, brandId)).thenReturn(List.of(product));

        //THEN
        Optional<Product> result = service.findProductPriceByDate(date, productId, brandId);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getProductId()).isEqualTo(1);
    }

    @DisplayName("When retrieving price by date product and brand returns the one with highest priority")
    @Test
    void whenRetrievingPriceByDateProductAndBrandReturnsTheOneWithHighestPriority() {
        //GIVEN
        Product productWithLowerPriority = generateModel(1);
        Product modelWithHigherPriority = generateModel(2);

        LocalDateTime date = LocalDateTime.of(2022, 10, 10, 10, 10, 10);
        Integer productId = 1;
        Integer brandId = 1;

        //WHEN
        when(productOutputPort.findProductsByDate(date, productId, brandId)).thenReturn(List.of(productWithLowerPriority, modelWithHigherPriority));

        //THEN
        Optional<Product> resultOptional = service.findProductPriceByDate(date, productId, brandId);
        assertThat(resultOptional.isPresent()).isTrue();
        Product result = resultOptional.get();
        assertThat(result.getPriority()).isEqualTo(2);
    }

    Product generateModel() {
        return Product.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2022, 10, 02, 10, 10, 10))
                .endDate(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .priceList(1)
                .productId(1)
                .price(3.50)
                .currency("euro")
                .build();
    }

    Product generateModel(Integer priority) {
        return Product.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2022, 10, 02, 10, 10, 10))
                .endDate(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .priceList(1)
                .productId(1)
                .price(3.50)
                .currency("euro")
                .priority(priority)
                .build();
    }
}