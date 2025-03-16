package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.Payout;
import com.affiliateSWD.affiliate_marketing.entity.TotalClicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PayoutRepository extends JpaRepository<Payout, Long> {
}
