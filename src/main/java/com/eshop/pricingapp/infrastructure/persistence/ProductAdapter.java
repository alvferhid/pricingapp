package com.eshop.pricingapp.infrastructure.persistence;

import com.eshop.pricingapp.domain.model.Product;
import com.eshop.pricingapp.infrastructure.persistence.mapper.ProductOutputMapper;
import com.eshop.pricingapp.infrastructure.persistence.repository.ProductRepository;
import com.eshop.pricingapp.ports.out.ProductOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAdapter implements ProductOutputPort {

    private final ProductOutputMapper productOutputMapper;
    private final ProductRepository productRepository;

    private final int MINIMUM_VALUE = 1;

    public List<Product> getAllProductPrices() {
        return productOutputMapper.toModel(productRepository.findAllProductPrices());
    }

    @Override
    public Optional<Product> findProductPriceByDate(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        checkValues(applicationDate, productId, brandId);
        return productRepository.findProductPriceByDate(applicationDate, productId, brandId)
                .map(productOutputMapper::toModel);
    }

    private void checkValues(LocalDateTime applicationdate, Integer productId, Integer brandId) {
        if (applicationdate == null || productId == null || brandId == null) {
            log.info("applicationDate = {}, productId = {}, brandId = {} ", applicationdate, productId, brandId);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Null values");
        }
        if (brandId < MINIMUM_VALUE || productId < MINIMUM_VALUE) {
            log.info("brandId value = {}, productId value = {}", brandId, productId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand Id and product ID valuest must be above 0");
        }
    }
}
