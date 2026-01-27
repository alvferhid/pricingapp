package com.eshop.pricingapp.infrastructure.persistence.repository;

import com.eshop.pricingapp.infrastructure.persistence.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProductRepository {

    @Select("SELECT brand_id, start_date, end_date, price_list, product_id, priority, price, curr, last_update, last_update_by FROM prices")
    @Results(value = {
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "priceList", column = "price_list"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "price", column = "price"),
            @Result(property = "currency", column = "curr"),
            @Result(property = "lastUpdate", column = "last_update"),
            @Result(property = "lastUpdateBy", column = "last_update_by")
    })
    List<ProductEntity> findAllProducts();

    @Select("""
                SELECT product_id, brand_id, price, curr, start_date, end_date, priority
                FROM prices
                WHERE product_id = #{productId}
                  AND brand_id = #{brandId}
                  AND #{applicationDate} BETWEEN start_date AND end_date
            """)
    @Results(value = {
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_Date"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "price", column = "price"),
            @Result(property = "currency", column = "curr"),
    })
    List<ProductEntity> findproductsByDate(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
