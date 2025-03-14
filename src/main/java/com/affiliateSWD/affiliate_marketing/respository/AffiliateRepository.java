package com.affiliateSWD.affiliate_marketing.respository;


import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AffiliateRepository extends JpaRepository<AffiliateLink, Long> {
    Optional<AffiliateLink> findByPublisherAffiliateIdAndCampaignAffiliateId(Long publisherId, Long campaignId);
}
