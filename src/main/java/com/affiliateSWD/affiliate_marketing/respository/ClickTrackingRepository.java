package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ClickTrackingRepository extends JpaRepository<Clicks, Long> {
    @Query("SELECT c FROM Clicks c WHERE c.affiliateLinkClick = :affiliateLink AND c.ipAddress = :ipAddress ORDER BY c.id ASC LIMIT 1")
    Optional<Clicks> findFirstByAffiliateLinkClickAndIpAddress(@Param("affiliateLink") AffiliateLink affiliateLink, 
                                                                @Param("ipAddress") String ipAddress);    
                                                                @Query("SELECT c FROM Clicks c WHERE c.affiliateLinkClick.id = :affiliateId AND c.ipAddress = :ipAddress AND c.timeClick >= :fiveMinutesAgo ORDER BY c.timeClick DESC")
    List<Clicks> findRecentClicks(@Param("affiliateId") Long affiliateId, @Param("ipAddress") String ipAddress, @Param("fiveMinutesAgo") LocalDateTime fiveMinutesAgo);


    long countByAffiliateLinkClickId(Long id);
}
