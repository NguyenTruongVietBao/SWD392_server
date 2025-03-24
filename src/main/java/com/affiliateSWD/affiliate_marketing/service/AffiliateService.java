package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AffiliateStatus;
import com.affiliateSWD.affiliate_marketing.respository.AffiliateRepository;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
import com.affiliateSWD.affiliate_marketing.respository.PublisherRepository;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;


@Service

public class AffiliateService {

    @Autowired
    AffiliateRepository affiliateRepository;

    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AccountUtils accountUtils;

    public String createAffiliateLink(Long campaignId) {
        Campaign existingCampaign = campaignRepository.findById(campaignId).orElse(null);;
        if (existingCampaign != null) {
//            String originUrl = "https://swd392-server.onrender.com/";
            String originUrl = "http://localhost:5173/redirect?";
            Long acid = accountUtils.getAccountCurrent().getId();
            Publisher publisher = publisherRepository.findByAccountPublisherId(acid).orElse(null);
            String combinedInfo = publisher.getId() + "_" + campaignId;

            String encodedAffiliateId = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(combinedInfo.getBytes(StandardCharsets.UTF_8))
                    .replace("/", "_")
                    .replace("+", "-");

            String trackingUrl = originUrl + "aff_id=" + encodedAffiliateId;

            AffiliateLink affiliateLink = new AffiliateLink();
            affiliateLink.setTrackingUrl(trackingUrl);
            affiliateLink.setStatus(AffiliateStatus.ACTIVE);
            affiliateLink.setCreateAt(LocalDateTime.now());
            affiliateLink.setPublisherAffiliate(accountUtils.getAccountCurrent().getPublisher());
            affiliateLink.setCampaignAffiliate(existingCampaign);
            affiliateRepository.save(affiliateLink);
            return originUrl + "aff_id=" + encodedAffiliateId;
        }
        return null;
    }

    public String[] getTwoId(String aff_id) {
        try {
            String safeAffId = aff_id.replace("-", "+").replace("_", "/");

            byte[] decodedBytes = Base64.getUrlDecoder().decode(safeAffId);
            String decodedAffiliateId = new String(decodedBytes, StandardCharsets.UTF_8);

            String[] parts = decodedAffiliateId.split("_");
            if (parts.length != 2) {
                return null;
            }
            return parts;

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 encoding: " + e.getMessage());
            return null;
        }
    }

    public Optional<AffiliateLink> getTwoData(Long publisherId, Long campaignId) {
        return affiliateRepository.findByPublisherAffiliateIdAndCampaignAffiliateId(publisherId, campaignId);
    }
}
