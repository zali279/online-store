package com.store.demo.service;


import com.store.demo.dto.ProductDTO;
import com.store.demo.mapper.ProductMapper;
import com.store.demo.model.Product;
import com.store.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
      private ProductRepository productRepository;
      private ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> getAllProduct(double minPrice, double maxPrice, boolean discount, String sortedBy){

        List<Product> productList = productRepository.findByPriceBetween(minPrice,maxPrice);

        if (discount) {
            productList = productRepository.findByPriceBetweenAndDiscounted(minPrice, maxPrice, discount);
        } else {
            productList = productRepository.findByPriceBetween(minPrice, maxPrice);
        }

        List<ProductDTO> productDTOList=productList.stream().map(p -> productMapper.toProductDTO(p)).toList();

        if("price".equals(sortedBy)){
//            productDTOList=productDTOList.stream().sorted(Comparator.comparingDouble(ProductDTO::getPriceAfterDiscount)).toList();
        } else if ("newest".equals(sortedBy)) {
            productDTOList=productDTOList.stream().sorted(Comparator.comparing(ProductDTO::getCreatedAt)).toList();
        }

        return productDTOList;
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

}
