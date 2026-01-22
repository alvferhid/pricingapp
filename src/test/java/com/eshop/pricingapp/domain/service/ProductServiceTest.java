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
    void whenGettingAllProductsReturnsListProducts(){
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
    void whenFindingProductByDateProductIdAndBrandIdReturnsOptional(){
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2022,10,10,10,10,10);
        Integer productId = 1;
        Integer brandId = 1;
        Product product = generateModel();

        //WHEN
        when(productOutputPort.findProductPriceByDate(date,productId,brandId)).thenReturn(Optional.of(product));

        //THEN
        Optional<Product> result = service.findProductPriceByDate(date,productId,brandId);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getProductId()).isEqualTo(1);
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
}