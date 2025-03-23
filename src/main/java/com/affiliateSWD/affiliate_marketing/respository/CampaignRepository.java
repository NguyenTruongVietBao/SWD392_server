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

    @Query("SELECT c FROM Campaign c " +
            "JOIN c.affiliateLinks al " +
            "WHERE al.publisherAffiliate.id = :publisherId")
    List<Campaign> findCampaignsByAffiliate(@Param("publisherId") Long publisherId);

    @Query("SELECT c FROM Campaign c WHERE c.advertisersCampaign.id = :advertiserId")
    List<Campaign> findCampaignsByAdvertiser(@Param("advertiserId") Long advertiserId);

    @Query("SELECT c FROM Campaign c WHERE c.advertisersCampaign.id = :advertiserId AND c.status = :status")
    List<Campaign> findCampaignsByAdvertiserAndStatus(@Param("advertiserId") Long advertiserId,
                                                      @Param("status") CampaignStatus status);

    @Query("SELECT COUNT(c) FROM Campaign c")
    long countTotalCampaigns();

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = 'APPROVED'")
    long countApprovedCampaigns();

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = 'PENDING'")
    long countPendingCampaigns();

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = :status ")
    long countStatusCampaigns(@Param("status") CampaignStatus status);


}
