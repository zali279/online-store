package com.store.demo.controller;

import com.store.demo.model.Order;
import com.store.demo.repository.OrderRepository;
import com.store.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/history")
    public ResponseEntity<?> orderHistory(){
        return ResponseEntity.ok(orderService.getOrderHistory());
    }

    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<Resource> getReceiptFile(@PathVariable Long orderId) throws IOException {
        return orderService.getReceiptFile(orderId);
    }

}
