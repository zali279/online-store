package com.store.demo.service;


import com.store.demo.model.Order;
import com.store.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getOrderHistory(){
        LocalDateTime now = LocalDateTime.now();
        return orderRepository.findByCreatedAtBefore(now);
    }
}
