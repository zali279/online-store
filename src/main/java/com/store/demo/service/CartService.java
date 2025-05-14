package com.store.demo.service;


import com.store.demo.model.*;
import com.store.demo.repository.*;
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
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public CartItem addToCart(Long productId,String email){
        //find user by email
        User user = userRepository.findByEmail(email);

        //find product by id
        Product product = productRepository.findById(productId).get();

        //check if user have cart
        Optional<Cart> cartOptional =cartRepository.findByUser(user);
        Cart cart;
        if(cartOptional.isPresent()){
            cart=cartOptional.get();
        }else {
            cart=new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<CartItem> items = cart.getItems();

        //create cart item
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setStatus("Pending");
        item.setQuantity(1);//need to check if available before or not
        cartItemRepository.save(item);

        //check discount in product
        if(product.isDiscounted() && product.getDiscountAmount() > threshold){
            //call the camunda to approve
            Map<String, Object> review = new HashMap<>();
            review.put("itemId", item.getId());
            review.put("email",email);
            runtimeService.startProcessInstanceByKey("OrderReview", review);
        }else {
            //change the status to approve
            item.setStatus("Approved");

        }

        return item;
    }

    public String checkout(String email) throws IOException {
        //find user by email
        User user= userRepository.findByEmail(email);

        Cart cart = user.getCart();

        //loop over item to check status
        // Check if any item is not approved
        List<CartItem> notApprovedItems = cart.getItems().stream()
                .filter(item -> item.getStatus().equalsIgnoreCase("pending") || item.getStatus().equalsIgnoreCase("rejected"))
                .toList();

        if (!notApprovedItems.isEmpty()) {
            // send email about unapproved items
            return "Some items need approval or rejected !";
        }

        //all item approved
        //create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus("completed");
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            totalAmount += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order); // This will also save OrderItems due to cascade


        //create receipt
        PdfGenerator.generateReceipt(cart.getItems(),"receipt.pdf");

        //send email with receipt
        emailSender.sendReceipt(email,"Order Approved" ,"Your order has been approved","receipt.pdf");

        return "order complete!";
    }

    public Cart getMyCart( String email) {
        //find user by email
        User user= userRepository.findByEmail(email);

        return user.getCart();

    }

    }
