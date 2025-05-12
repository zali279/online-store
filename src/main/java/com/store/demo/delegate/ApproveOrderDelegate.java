package com.store.demo.delegate;

import com.store.demo.model.Cart;
import com.store.demo.repository.CartRepository;
import com.store.demo.service.EmailSender;
import com.store.demo.service.PdfGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component("approveOrderDelegate" )
public class ApproveOrderDelegate implements JavaDelegate {

    private CartRepository cartRepository;
    private EmailSender emailSender;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long orderId = (Long) delegateExecution.getVariable("orderId");
        String email = (String) delegateExecution.getVariable("email");

        Cart cart = cartRepository.findById(orderId).orElseThrow();
        cart.setStatus("APPROVED");
        cartRepository.save(cart);

        emailSender.sendEmail(email,"Order Approved", "Your order has been approved .");

        //send pdf
        PdfGenerator.generateReceipt(cart.getItems(),"test.pdf");

    }
}
