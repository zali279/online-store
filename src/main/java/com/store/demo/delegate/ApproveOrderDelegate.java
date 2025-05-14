package com.store.demo.delegate;

import com.store.demo.model.CartItem;
import com.store.demo.repository.CartItemRepository;
import com.store.demo.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component("approveOrderDelegate" )
@RequiredArgsConstructor
public class ApproveOrderDelegate implements JavaDelegate {

    private final EmailSender emailSender;
    private final CartItemRepository cartItemRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long itemId = (Long) delegateExecution.getVariable("itemId");
        String email = (String) delegateExecution.getVariable("email");

        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow();
        cartItem.setStatus("APPROVED");
        cartItemRepository.save(cartItem);

        //send email
        emailSender.sendEmail(email,"Item Approved", "the item has been approved .");

    }
}
