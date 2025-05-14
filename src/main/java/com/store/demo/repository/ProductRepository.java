package com.store.demo.repository;

import com.store.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long > {
    List<Product> findByPriceBetweenAndDiscounted(double min , double max , boolean discounted);
    List<Product> findByPriceBetween(double min , double max);
}
