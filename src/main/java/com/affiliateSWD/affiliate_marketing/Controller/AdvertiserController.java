package com.affiliateSWD.affiliate_marketing.Controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.model.request.PaymentRequest;
import com.affiliateSWD.affiliate_marketing.service.AdvertiserService;
import com.affiliateSWD.affiliate_marketing.service.AuthenticationService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/advertisers")
public class AdvertiserController {

    @Autowired
    private  AdvertiserService advertiserService;

     @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/payment")
    public Map<String, String> createPaymentIntent(
        @Valid @RequestBody PaymentRequest payment,        
        Principal principal) throws StripeException {
        String username = principal.getName(); 
        Account account = authenticationService.findByUsername(username);
        Long Id = account.getId();
        System.out.println("Received payment request: " + payment);
        
        return advertiserService.processPayment(Id, payment.getAmount());
    }
}
