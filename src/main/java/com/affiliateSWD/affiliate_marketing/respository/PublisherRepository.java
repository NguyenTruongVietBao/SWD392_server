package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query("SELECT p FROM Publisher p JOIN p.affiliateLinks al WHERE al.campaignAffiliate.id = :campaignId")
    List<Publisher> findAllPublishersByCampaignId(Long campaignId);
}