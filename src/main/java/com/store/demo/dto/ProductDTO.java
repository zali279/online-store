package com.store.demo.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private String name;
    private  double price;
    private double priceAfterDiscount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

//    public void setPriceAfterDiscount(double priceAfterDiscount) {
//        this.priceAfterDiscount = BigDecimal.valueOf(priceAfterDiscount)
//                .setScale(3, RoundingMode.HALF_UP)
//                .doubleValue();
//    }

}
