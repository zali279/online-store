package com.store.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean discounted;
    private double discountAmount;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
