package com.affiliateSWD.affiliate_marketing.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.affiliateSWD.affiliate_marketing.respository.AdminRepository;
import com.affiliateSWD.affiliate_marketing.respository.FraudDetectionRepository;
import com.affiliateSWD.affiliate_marketing.entity.Admin;
import com.affiliateSWD.affiliate_marketing.entity.FraudDetection;
import com.affiliateSWD.affiliate_marketing.enums.FraudStatus;

@Service
public class FraudDetectionServices {
    
    @Autowired
    public FraudDetectionRepository fraudDetectionRepository;

    @Autowired
    private AdminRepository adminRepository;

    public List<FraudDetection> getAllFraudDetection() {
        return fraudDetectionRepository.findAll();
    }

    public FraudDetection getFraudDetectionById(Long id) {
        return fraudDetectionRepository.findById(id).orElse(null);
    }

    public FraudDetection upFraudDetectionStatus(Long id, FraudStatus status,Long adminId) {
        FraudDetection updatedFraudDetection = fraudDetectionRepository.findById(id).orElse(null);

        LocalDateTime approvedAt = LocalDateTime.now();

        Admin admin = adminRepository.findByAccountAdminId(adminId);
        if (admin == null) {
            return null;
        }
        if (updatedFraudDetection != null) {
            updatedFraudDetection.setReviewAt(approvedAt);
            updatedFraudDetection.setAdminFraud(admin);
            updatedFraudDetection.setStatus(status);
            return fraudDetectionRepository.save(updatedFraudDetection);
        }
        return null;
    }
}
