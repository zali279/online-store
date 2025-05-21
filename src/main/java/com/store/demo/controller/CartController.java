package com.store.demo.controller;

import com.store.demo.model.Cart;
import com.store.demo.model.CartItem;
import com.store.demo.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
   private final CartService cartService;

   @PostMapping("/{productId}")
   public ResponseEntity<CartItem> addToCart(@PathVariable Long productId, @RequestParam String email){
        return ResponseEntity.ok(cartService.addToCart(productId,email));
   }

   @GetMapping("")
   public ResponseEntity<Cart> getMyCart(@RequestParam String email){
      return ResponseEntity.ok(cartService.getMyCart(email));
   }


   @PostMapping("/checkout")
   public ResponseEntity<?> checkout (@RequestParam String email) throws IOException {
      return ResponseEntity.ok(cartService.checkout(email));
   }



}
