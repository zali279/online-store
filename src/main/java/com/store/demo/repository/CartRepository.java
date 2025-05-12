package com.store.demo.repository;

import com.store.demo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByCreatedAtBefore(LocalDateTime today);
}
