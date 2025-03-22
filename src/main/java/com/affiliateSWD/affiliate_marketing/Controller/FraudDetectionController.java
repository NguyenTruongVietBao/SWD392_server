package com.affiliateSWD.affiliate_marketing.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.affiliateSWD.affiliate_marketing.entity.FraudDetection;
import com.affiliateSWD.affiliate_marketing.service.FraudDetectionServices;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/fraud")

public class FraudDetectionController {
    @Autowired
    FraudDetectionServices fraudDetectionServices;
    @GetMapping
    public List<FraudDetection> getAllCampaigns() {
        return fraudDetectionServices.getAllFraudDetection();
    }
}
