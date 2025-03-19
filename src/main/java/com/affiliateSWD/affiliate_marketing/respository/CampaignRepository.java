package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.enums.AffiliateStatus;
import com.affiliateSWD.affiliate_marketing.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.affiliateSWD.affiliate_marketing.entity.Campaign;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("SELECT c FROM Campaign c " +
            "JOIN c.affiliateLinks al " +
            "WHERE al.status = :status " +
            "AND al.publisherAffiliate.id = :publisherId")
    List<Campaign> findCampaignsByAffiliateStatus(@Param("status") AffiliateStatus status,
                                                  @Param("publisherId") Long publisherId);
}
