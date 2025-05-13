package com.store.demo.controller;

import com.store.demo.service.CartService;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
   private CartService cartService;
   private final RuntimeService runtimeService;

//   public ResponseEntity<?> addToCart(@RequestBody Product product){
//        return null;
//   }

   @PostMapping("/{productId}")
   public ResponseEntity<?> placeOrder (@PathVariable Long productId , @RequestParam String email) throws IOException {
      return ResponseEntity.ok(cartService.placeOrder(productId,email));
   }

   @PostMapping("/checkout")
   public ResponseEntity<?> checkout (){
      return null;
   }

   @GetMapping("/history")
   public ResponseEntity<?> orderHistory(){
      return null;
   }

}
