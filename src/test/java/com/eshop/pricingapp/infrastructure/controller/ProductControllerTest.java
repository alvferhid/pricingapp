package com.eshop.pricingapp.infrastructure.controller;

import com.eshop.pricingapp.domain.mapper.ProductInputMapper;
import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.dto.ProductDto;
import com.eshop.pricingapp.ports.in.ProductInputPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductInputMapper mapper;

    @Mock
    ProductInputPort productInputPort;

    @InjectMocks
    ProductController controller;

    @DisplayName("When getting all product return ResponseEntity OK with list")
    @Test
    void whenGettingAllProductReturnResponseEntityOkWithList() {
        //GIVEN
        List<ProductDto> productDtos = List.of(generateDto());
        List<Product> products = List.of(generateModel());

        //WHEN
        when(productInputPort.getAllProducts()).thenReturn(products);
        when(mapper.toDto(products)).thenReturn(productDtos);

        //THEN
        ResponseEntity<List<ProductDto>> result = controller.getAllProducts();

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(result.getBody());
        assertThat(result.getBody().size()).isEqualTo(1);
    }

    @DisplayName("Given correct date product and brand returns OK with price")
    @Test
    void givenCorrectDateProductAndBrandReturnsOkWithPrice() {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2022, 10, 02, 10, 10, 10);
        Integer productId = 1;
        Integer brandId = 1;
        Product productPrice = generateModel();
        ProductDto productPriceDto = generateDto();

        //WHEN
        when(productInputPort.findProductPriceByDate(date, productId, brandId)).thenReturn(Optional.of(productPrice));
        when(mapper.toDto(productPrice)).thenReturn(productPriceDto);

        //THEN
        ResponseEntity<ProductDto> result = controller.findProductPriceByDate(date, productId, brandId);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(result.getBody());
        assertThat(result.getBody().getBrandId()).isEqualTo(1);
        assertThat(result.getBody().getStartDate()).isEqualTo(date);
    }

    @DisplayName("Given incorrect date, product, or brand returns Not Found")
    @Test
    void givenIncorrectDateProductOrBrandReturnsNotFound() {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2022, 10, 15, 10, 10, 10); // Utiliza una fecha que no coincida con los datos de prueba
        Integer productId = 2;
        Integer brandId = 2;

        //WHEN
        when(productInputPort.findProductPriceByDate(date, productId, brandId)).thenReturn(Optional.empty());

        //THEN
        ResponseEntity<ProductDto> result = controller.findProductPriceByDate(date, productId, brandId);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    ProductDto generateDto() {
        return ProductDto.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2022, 10, 02, 10, 10, 10))
                .endDate(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .priceList(1)
                .productId(1)
                .price(3.50)
                .currency("euro")
                .build();
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