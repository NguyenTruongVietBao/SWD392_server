package com.affiliateSWD.affiliate_marketing.respository;


import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;

import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AffiliateRepository extends JpaRepository<AffiliateLink, Long> {
    Optional<AffiliateLink> findByPublisherAffiliateIdAndCampaignAffiliateId(Long publisherId, Long campaignId);

    @Query("SELECT c FROM Clicks c WHERE c.affiliateLinkClick.publisherAffiliate.id = :publisherId")
    List<Clicks> findClicksByPublisherId(Long publisherId);

    List<AffiliateLink> findByPublisherAffiliateId(Long publisherId);
}
