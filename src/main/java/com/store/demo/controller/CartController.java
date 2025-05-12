package com.store.demo.controller;

import com.store.demo.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
   private CartService cartService;

//   public ResponseEntity<?> addToCart(@RequestBody Product product){
//        return null;
//   }

   @PostMapping("/{productId}")
   public ResponseEntity<?> placeOrder (@PathVariable Long productId , @RequestParam String email) throws IOException {

      return ResponseEntity.ok(cartService.placeOrder(productId,email));
      //after this create pdf

   }

   @GetMapping("/history")
   public ResponseEntity<?> orderHistory(){
      return null;

   }
}
