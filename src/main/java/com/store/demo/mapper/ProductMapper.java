package com.store.demo.mapper;

import com.store.demo.dto.ProductDTO;
import com.store.demo.model.Product;

import java.time.LocalDateTime;

public class ProductMapper {
    public static ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setPriceAfterDiscount(product.isDiscounted()
                    ? product.getPrice() - (product.getDiscountPercentage()*product.getPrice()/100)
                    : product.getPrice());
        dto.setCreatedAt(LocalDateTime.now());
        return dto;
    }
}


