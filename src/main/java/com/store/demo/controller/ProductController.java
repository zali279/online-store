package com.store.demo.controller;

import com.store.demo.model.Product;
import com.store.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProduct(
            @RequestParam(required = false ,defaultValue = "0" ) double minPrice,
            @RequestParam(required = false, defaultValue = "" +Double.MAX_VALUE ) double maxPrice,
            @RequestParam(required = false) boolean discount,
            @RequestParam(required = false) String sortedBy
    ){
        return ResponseEntity.ok(productService.getAllProduct( minPrice,maxPrice,discount,sortedBy));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.createProduct(product));
    }

}
