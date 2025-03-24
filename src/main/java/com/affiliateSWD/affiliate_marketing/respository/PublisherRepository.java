package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository


public interface PublisherRepository extends JpaRepository<Publisher, Long>
{
    Optional<Publisher> findByAccountPublisherId(Long accountId);
    @Query("SELECT p FROM Publisher p JOIN p.affiliateLinks al WHERE al.campaignAffiliate.id = :campaignId")
    List<Publisher> findAllPublishersByCampaignId(Long campaignId);

    @Query("SELECT p.id FROM Publisher p WHERE p.accountPublisher.id = :accountId")
    Long findPublisherIdByAccountId(Long accountId);

}