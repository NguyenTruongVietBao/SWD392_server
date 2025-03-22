package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.FraudDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FraudRepository extends JpaRepository<FraudDetection, Long> {
}
