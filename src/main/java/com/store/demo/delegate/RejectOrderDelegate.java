package com.store.demo.delegate;

import com.store.demo.model.Cart;
import com.store.demo.repository.CartRepository;
import com.store.demo.service.EmailSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component("rejectOrderDelegate" )
public class RejectOrderDelegate implements JavaDelegate {
    private CartRepository cartRepository;
    private EmailSender emailSender;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long orderId = (Long) delegateExecution.getVariable("orderId");
        String email = (String) delegateExecution.getVariable("email");

        Cart cart = cartRepository.findById(orderId).orElseThrow();
        cart.setStatus("Rejected");
        cartRepository.save(cart);

        emailSender.sendEmail(email,"Order Rejected", "Your order was not approved.");

    }
}
