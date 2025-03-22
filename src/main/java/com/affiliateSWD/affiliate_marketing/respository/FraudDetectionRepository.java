package com.affiliateSWD.affiliate_marketing.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.affiliateSWD.affiliate_marketing.entity.FraudDetection;

public interface FraudDetectionRepository extends JpaRepository<FraudDetection, Long> {
}
