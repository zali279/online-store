package com.store.demo.service;


import com.store.demo.model.Order;
import com.store.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getOrderHistory(){
        LocalDateTime now = LocalDateTime.now();
        return orderRepository.findByCreatedAtBefore(now);
    }

    public ResponseEntity<Resource> getReceiptFile(Long orderId) throws IOException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty() || optionalOrder.get().getReceiptPath() == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(optionalOrder.get().getReceiptPath());
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }
}
