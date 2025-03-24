package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.respository.AffiliateRepository;
import com.affiliateSWD.affiliate_marketing.respository.ClickTrackingRepository;
import com.affiliateSWD.affiliate_marketing.respository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private ClickTrackingRepository clickTrackingRepository;

    public List<Account> getPublishersByCampaignId(Long campaignId) {
        List<Publisher> publishers = publisherRepository.findAllPublishersByCampaignId(campaignId);
        return publishers.stream()
                .map(Publisher::getAccountPublisher)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getPublisherCampaignStats(Long accountId) {
        Long publisherId = publisherRepository.findPublisherIdByAccountId(accountId);
        Map<String, Object> result = new HashMap<>();

        Publisher publisher = publisherRepository.findById(publisherId).orElse(null);
        if (publisher == null) {
            result.put("message", "Publisher not found");
            return result;
        }

        List<AffiliateLink> affiliateLinks = affiliateRepository.findByPublisherAffiliateId(publisherId);

        for (AffiliateLink affiliateLink : affiliateLinks) {
            Map<String, Object> affiliateStats = new HashMap<>();

            long totalClicks = clickTrackingRepository.countByAffiliateLinkClickId(affiliateLink.getId());

            float income = totalClicks * affiliateLink.getCampaignAffiliate().getCommissionValue();

            affiliateStats.put("AffiliateLink", affiliateLink.getTrackingUrl());
            affiliateStats.put("TotalClicks", totalClicks);
            affiliateStats.put("Income", income);

            result.put(affiliateLink.getCampaignAffiliate().getTitle(), affiliateStats);
        }
        return result;
    }
}
