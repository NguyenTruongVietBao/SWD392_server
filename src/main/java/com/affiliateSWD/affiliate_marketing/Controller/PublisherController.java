package com.affiliateSWD.affiliate_marketing.Controller;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publisher")

public class PublisherController {

    @Autowired
    CampaignService campaignService;

//    @PreAuthorize("hasAuthority('PUBLISHER')")
//    @GetMapping("/listCampaign")
//    public ResponseEntity<List<Campaign>> getAllPublisherCampaign() {
//        List<Campaign> campaigns = campaignService.getAllPublisherCampaign();
//        return ResponseEntity.ok(campaigns);
//    }

    @GetMapping("listCampaign/{id}")
    public ResponseEntity<List<Campaign>> getAllPublisherCampaign(Long id) {
        List<Campaign> campaigns = campaignService.getAllPublisherCampaigns(id);
        return ResponseEntity.ok(campaigns);
    }

}
