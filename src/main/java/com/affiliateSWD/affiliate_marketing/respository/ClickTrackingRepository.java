package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClickTrackingRepository extends JpaRepository<Clicks, Long> {
    Optional<Clicks> findByAffiliateLinkClickAndIpAddress(AffiliateLink affiliateLink, String ipAddress);
}
