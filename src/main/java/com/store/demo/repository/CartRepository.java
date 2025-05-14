package com.store.demo.repository;

import com.store.demo.model.Cart;
import com.store.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByCreatedAtBefore(LocalDateTime today);
    Optional<Cart> findByUser(User user);
}
