package com.store.demo.service;


import com.store.demo.model.Cart;
import com.store.demo.model.Product;
import com.store.demo.model.User;
import com.store.demo.repository.CartRepository;
import com.store.demo.repository.ProductRepository;
import com.store.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    @Value("${discount.threshold}")
    private double threshold;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final RuntimeService runtimeService;
    private final CartRepository cartRepository;

    public Cart placeOrder(Long productId , String email) throws IOException {
        //find user + product
        Optional<Product> product = productRepository.findById(productId);
        User user = userRepository.findByEmail(email);

        //create the cart
        Cart cart = new Cart();
        cart.setStatus("PENDING");
        cart.setUser(user);
        List<Product> items =new ArrayList<>();
        items.add(product.get());

        if(product.get().isDiscounted() && product.get().getDiscountAmount() > threshold){
            //call the camunda to approve
            Map<String, Object> review = new HashMap<>();
            review.put("orderId", cart.getId());
            review.put("email",email);

            runtimeService.startProcessInstanceByKey("OrderReview", review);


        }else {
            //change the status to approve
             cart.setStatus("Approved");

             //send pdf
            PdfGenerator.generateReceipt(items,"test.pdf");

            //send email
            emailSender.sendReceipt(email,"Order Approved" ,"Your order has been approved","test.pdf");
        }


        return cart;
    }

    public List<Cart> getOrderHistory(){
        LocalDateTime now = LocalDateTime.now();
        return cartRepository.findByCreatedAtBefore(now);
    }

}
