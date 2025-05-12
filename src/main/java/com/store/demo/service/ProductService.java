package com.store.demo.service;


import com.store.demo.model.Product;
import com.store.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
      private ProductRepository productRepository;


    @GetMapping
    public Page<Product> getAllProduct(double minPrice, double maxPrice, boolean discount, String sortedBy){
         Page<Product> products;
        Pageable pageable = PageRequest.of(1, 5);
        if (discount == true || discount == false) {
            products = productRepository.findByPriceBetweenAndDiscounted(minPrice, maxPrice, discount,pageable);
        } else {
            products = productRepository.findByPriceBetween(minPrice, maxPrice,pageable);
        }

        if(sortedBy.equals("price")){
            products.map(product -> product).stream().sorted(Comparator.comparingDouble(Product::getPrice));
        } else if (sortedBy.equals("newest")) {
            products.map(product -> product).stream().sorted(Comparator.comparing(Product::getCreatedAt));
        }

        return products;
    }
}
