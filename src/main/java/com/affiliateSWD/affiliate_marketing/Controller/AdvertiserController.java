package com.affiliateSWD.affiliate_marketing.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Advertisers;
import com.affiliateSWD.affiliate_marketing.model.request.PaymentRequest;
import com.affiliateSWD.affiliate_marketing.service.AdvertiserService;
import com.affiliateSWD.affiliate_marketing.service.AuthenticationService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/advertisers")
public class AdvertiserController {

    @Autowired
    private  AdvertiserService advertiserService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CampaignService campaignService;

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

//    @PreAuthorize("hasAuthority('ADVERTISERS')")
//    @GetMapping("listCampaign")
//    public ResponseEntity<List<Campaign>> getAllAdvertiserCampaign() {
//        List<Campaign> campaigns = campaignService.getAllAdvertiserCampaign();
//        return ResponseEntity.ok(campaigns);
//    }

//    @PreAuthorize("hasAuthority('ADVERTISERS')")
    @GetMapping("listCampaign/{id}")
    public ResponseEntity<List<Campaign>> getAllAdvertiserCampaign(Long id) {
        List<Campaign> campaigns = campaignService.getAllAdvertiserCampaigns(id);
        return ResponseEntity.ok(campaigns);
    }
    @GetMapping("/advertiser")
    public ResponseEntity<Advertisers> getAdverByAccountid(Principal principal) {
         String username = principal.getName(); 
        Account account = authenticationService.findByUsername(username);
        Long Id = account.getId();
        Advertisers advertisers = advertiserService.getAdvertiser(Id);
        return ResponseEntity.ok(advertisers);
}
}
