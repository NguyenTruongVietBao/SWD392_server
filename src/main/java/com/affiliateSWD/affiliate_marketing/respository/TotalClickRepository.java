package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import com.affiliateSWD.affiliate_marketing.entity.TotalClicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface TotalClickRepository extends JpaRepository<TotalClicks, Long> {
    Optional<TotalClicks> findByAffiliateLinkTotal(AffiliateLink affiliateLink);
}
