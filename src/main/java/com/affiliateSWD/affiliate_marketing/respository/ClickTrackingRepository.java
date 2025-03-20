package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClickTrackingRepository extends JpaRepository<Clicks, Long> {
    @Query("SELECT c FROM Clicks c WHERE c.affiliateLinkClick = :affiliateLink AND c.ipAddress = :ipAddress ORDER BY c.id ASC LIMIT 1")
    Optional<Clicks> findFirstByAffiliateLinkClickAndIpAddress(@Param("affiliateLink") AffiliateLink affiliateLink, 
                                                                @Param("ipAddress") String ipAddress);    
}
