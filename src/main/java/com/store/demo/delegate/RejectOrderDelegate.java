package com.store.demo.delegate;

import com.store.demo.model.CartItem;
import com.store.demo.repository.CartItemRepository;
import com.store.demo.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component("rejectOrderDelegate" )
@RequiredArgsConstructor
public class RejectOrderDelegate implements JavaDelegate {
    private final EmailSender emailSender;
    private final CartItemRepository cartItemRepository;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long itemId = (Long) delegateExecution.getVariable("itemId");
        String email = (String) delegateExecution.getVariable("email");

        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow();
        cartItem.setStatus("Rejected");
        cartItemRepository.save(cartItem);

        emailSender.sendEmail(email,"Order Rejected", "Your order was not approved.");

    }
}
