package com.affiliateSWD.affiliate_marketing.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.affiliateSWD.affiliate_marketing.entity.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
