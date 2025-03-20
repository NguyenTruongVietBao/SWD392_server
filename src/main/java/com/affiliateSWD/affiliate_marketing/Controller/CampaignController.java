package com.affiliateSWD.affiliate_marketing.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;

import com.affiliateSWD.affiliate_marketing.entity.*;
import com.affiliateSWD.affiliate_marketing.enums.CampaignStatus;
import com.affiliateSWD.affiliate_marketing.model.request.CampaignRequest;
import com.affiliateSWD.affiliate_marketing.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/advertiser/campaigns")

public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AffiliateService affiliateService;

    @Autowired
    private ClickTrackingService clickTrackingService;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private TotalClickService totalClickService;

    @Autowired
    private TransactionService transactionService;


    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        Campaign campaign = campaignService.getCampaignById(id);
        return ResponseEntity.ok(campaign);   
    }
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateCampaignStatus(
    @PathVariable Long id, 
    @RequestParam CampaignStatus status,
    Principal principal) {
        try {
            String username = principal.getName(); 
            Account account = authenticationService.findByUsername(username);

            Long adminId = account.getId();
    
            Campaign updatedCampaign = campaignService.updateCampaignStatus(id, status, adminId);
            return ResponseEntity.ok(updatedCampaign);
        } catch (RuntimeException e) {
            
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCampaign(@RequestBody CampaignRequest campaign, Principal principal) {
        String username = principal.getName(); 
        Account account = authenticationService.findByUsername(username);

        if (account == null || !AccountRoles.ADVERTISERS.equals(account.getRole())) {    
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only advertisers can create campaigns");
    }
        Long Id = account.getId();

        Campaign data = campaignService.createCampaign(campaign, Id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Campaign created successfully");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaignDetails) {
        try {
            Campaign updatedCampaign = campaignService.updateCampaign(id, campaignDetails);
            return ResponseEntity.ok(updatedCampaign);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        if (campaignService.deleteCampaign(id)) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build(); 
    }

    // @PutMapping("changeStatus/{id}")
    // public ResponseEntity<Campaign> changeStatusCampaign(@PathVariable Long id, CampaignStatus status) {
    //     try {
    //         Campaign changeCampaign = campaignService.statusCampaign(id, status);
    //         return ResponseEntity.ok(changeCampaign);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @PostMapping("/generateLink/{campaignId}")
    public ResponseEntity<String> generateAffiliateLink(@PathVariable Long campaignId) {
        try {
            String affiliateLink = affiliateService.createAffiliateLink(campaignId);
            return ResponseEntity.ok(affiliateLink);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/affiliateLink/redirect")
    public ResponseEntity<String> trackAndRedirect(@RequestParam("aff_id") String aff_id, HttpServletRequest request) {        try {
            String safeAffId = aff_id.replace("-", "+").replace("_", "/");

            byte[] decodedBytes = Base64.getUrlDecoder().decode(safeAffId);
            String decodedAffiliateId = new String(decodedBytes, StandardCharsets.UTF_8);

            String[] parts = decodedAffiliateId.split("_");
            if (parts.length != 2) {
                return ResponseEntity.badRequest().build();
            }

            Long publisherId = Long.parseLong(parts[0]);
            Long campaignId  = Long.parseLong(parts[1]);

            Optional<AffiliateLink> affiliateLink = affiliateService.getTwoData(publisherId, campaignId);
            if (affiliateLink.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

        Transaction transaction = transactionService.createTransaction(affiliateLink.orElse(null), request);
           if (transaction == null){
               return ResponseEntity.notFound().build();
            }

            totalClickService.incrementClickCount(affiliateLink.orElse(null));

            String redirectUrl = affiliateLink.get().getCampaignAffiliate().getAdsLink();
            
            // Trả về link thay vì điều hướng
            return ResponseEntity.ok(redirectUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/check/{publisherId}/{campaignId}")
    public ResponseEntity<Optional<AffiliateLink>> getCampaignById(@PathVariable Long publisherId, Long campaignId) {
        Optional<AffiliateLink> affiliateLink = affiliateService.getTwoData(publisherId, campaignId);
        return ResponseEntity.ok(affiliateLink);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getCampaignStats() {
        Map<String, Long> total = campaignService.getCampaignStats();
        return ResponseEntity.ok(total);
    }
}
