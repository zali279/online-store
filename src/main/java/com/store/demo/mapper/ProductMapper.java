package com.store.demo.mapper;

import com.store.demo.dto.ProductDTO;
import com.store.demo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface ProductMapper {

     @Mapping(target = "priceAfterDiscount", expression = "java(calculatePriceAfterDiscount(product.getPrice(), product.getDiscountPercentage()))")
     ProductDTO toProductDTO(Product product);

     default double calculatePriceAfterDiscount(double price, double discountPercentage) {
          double discountedPrice = price - (price * discountPercentage / 100);
          return BigDecimal.valueOf(discountedPrice)
                  .setScale(3, RoundingMode.HALF_UP)
                  .doubleValue();
     }
}
