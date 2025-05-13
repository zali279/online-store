package com.store.demo.service;


import com.store.demo.model.Product;
import com.store.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
      private ProductRepository productRepository;


    @GetMapping
    public Page<Product> getAllProduct(double minPrice, double maxPrice, boolean discount, String sortedBy){
        Page<Product> products;
        Pageable pageable = PageRequest.of(0, 5);

        List<Product> productList = productRepository.findByPriceBetween(minPrice,maxPrice);
        productList.forEach(p -> System.out.println(p));



        if (discount == true || discount == false) {
            System.out.println("111111");
            products = productRepository.findByPriceBetweenAndDiscounted(minPrice, maxPrice, discount,pageable);
        } else {
            System.out.println("22222222");
            products = productRepository.findByPriceBetween(minPrice, maxPrice,pageable);
        }

        if("price".equals(sortedBy)){
            System.out.println("333333");
            products.map(product -> product).stream().sorted(Comparator.comparingDouble(Product::getPrice));
        } else if ("newest".equals(sortedBy)) {
            System.out.println("44444444");
            products.map(product -> product).stream().sorted(Comparator.comparing(Product::getCreatedAt));
        }

        return products;
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

}
