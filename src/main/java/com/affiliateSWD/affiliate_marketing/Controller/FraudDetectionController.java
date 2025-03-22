package com.affiliateSWD.affiliate_marketing.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.FraudDetection;
import com.affiliateSWD.affiliate_marketing.enums.FraudStatus;
import com.affiliateSWD.affiliate_marketing.service.AuthenticationService;
import com.affiliateSWD.affiliate_marketing.service.FraudDetectionServices;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/fraud")

public class FraudDetectionController {
    @Autowired
    FraudDetectionServices fraudDetectionServices;

    @Autowired
    private AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<List<FraudDetection>> getAllCampaigns() {
        List<FraudDetection> fraudDetections = fraudDetectionServices.getAllFraudDetection();
        return ResponseEntity.ok(fraudDetections);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FraudDetection> getFraudDetectionById(Long id) {
        FraudDetection fraud = fraudDetectionServices.getFraudDetectionById(id);
        return ResponseEntity.ok(fraud);
    }   

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<FraudDetection> updateFraudDetectionStatus(Long id, FraudStatus status,Principal principal) {
        String username = principal.getName(); 
        Account account = authenticationService.findByUsername(username);
        Long adminId = account.getId();
        FraudDetection updatedFraudDetection = fraudDetectionServices.upFraudDetectionStatus(id, status,adminId);
        return ResponseEntity.ok(updatedFraudDetection);
    }
}
