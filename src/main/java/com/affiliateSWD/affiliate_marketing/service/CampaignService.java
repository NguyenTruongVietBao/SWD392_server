package com.affiliateSWD.affiliate_marketing.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.enums.AffiliateStatus;
import com.affiliateSWD.affiliate_marketing.enums.CampaignStatus;

import com.affiliateSWD.affiliate_marketing.model.request.CampaignRequest;
import com.affiliateSWD.affiliate_marketing.respository.*;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.affiliateSWD.affiliate_marketing.entity.Admin;
import com.affiliateSWD.affiliate_marketing.entity.Advertisers;
import com.affiliateSWD.affiliate_marketing.entity.Campaign;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdvertisersRepository advertisersReposioty;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }
    public Campaign updateCampaignStatus(Long id, CampaignStatus status,Long adminCampaign) {
        Campaign updatedCampaign = campaignRepository.findById(id).orElse(null);

        Admin admin = adminRepository.findByAccountAdminId(adminCampaign);
        if (admin == null) {
            return null;
        }

        LocalDateTime approvedAt = LocalDateTime.now();

        if (updatedCampaign != null) {
            updatedCampaign.setStatus(status);
            updatedCampaign.setAdminCampaign(admin);
            updatedCampaign.setApprovedAt(approvedAt);
            return campaignRepository.save(updatedCampaign);
        }
        return null;
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id).orElse(null);
    }

    public Campaign createCampaign(CampaignRequest campaignRequest, Long Id) {

        Advertisers advertisers = advertisersReposioty.findByAccountAdvertisersId(Id);
        if (advertisers == null) {
            return null;
        }

        Campaign newCampaign = new Campaign();

        newCampaign.setTitle(campaignRequest.getTitle());
        newCampaign.setBudget(campaignRequest.getBudget());
        newCampaign.setDescription(campaignRequest.getDescription());
        newCampaign.setTargetAudience(campaignRequest.getTargetAudience());
        newCampaign.setCommissionRate(campaignRequest.getCommissionRate());
        newCampaign.setCommissionValue(campaignRequest.getCommissionValue());
        newCampaign.setRating(campaignRequest.getRating());
        newCampaign.setAdsLink(campaignRequest.getAdsLink());
        newCampaign.setImageUrl(campaignRequest.getImageUrl());
        newCampaign.setStartDate(campaignRequest.getStartDate());
        newCampaign.setAdvertisersCampaign(advertisers);
        newCampaign.setEndDate(campaignRequest.getEndDate());
        newCampaign.setStatus(CampaignStatus.valueOf("PENDING"));
        newCampaign.setCreatedAt(campaignRequest.getCreatedAt());
    
        return campaignRepository.save(newCampaign);
    }

    public Campaign updateCampaign(Long id,Campaign campaign) {
        Campaign existingCampaign = campaignRepository.findById(id).orElse(null);
        if (existingCampaign != null) {
            existingCampaign.setTitle(campaign.getTitle());
            existingCampaign.setBudget(campaign.getBudget());
            existingCampaign.setDescription(campaign.getDescription());
            existingCampaign.setTargetAudience(campaign.getTargetAudience());
            existingCampaign.setCommissionRate(campaign.getCommissionRate());
            existingCampaign.setCommissionValue(campaign.getCommissionValue());
            existingCampaign.setRating(campaign.getRating());
            existingCampaign.setAdsLink(campaign.getAdsLink());
            existingCampaign.setImageUrl(campaign.getImageUrl());
            existingCampaign.setStartDate(campaign.getStartDate());
            existingCampaign.setEndDate(campaign.getEndDate());
            existingCampaign.setStatus(campaign.getStatus());
            return campaignRepository.save(existingCampaign);
        }
        return null;
    }

    public boolean deleteCampaign(Long id) {
        if (campaignRepository.existsById(id)) {
            campaignRepository.deleteById(id);
            return true; 
        }
        return false; 
    }

    public List<Campaign> getAllPublisherCampaign() {
        List<Campaign> campaigns = campaignRepository.findCampaignsByAffiliateStatus(AffiliateStatus.ACTIVE, accountUtils.getAccountCurrent().getId());
        return campaigns;
    }

    public List<Campaign> getAllAdvertiserCampaign() {
        List<Campaign> campaigns = campaignRepository.findCampaignsByAdvertiserAndStatus(accountUtils.getAccountCurrent().getId(), CampaignStatus.APPROVED);
        return campaigns;
    }

    public Map<String, Long> getCampaignStats() {
        Map<String, Long> result = new HashMap<>();
        result.put("Total", campaignRepository.countTotalCampaigns());
        result.put("APPROVED", campaignRepository.countStatusCampaigns(CampaignStatus.APPROVED));
        result.put("PENDING", campaignRepository.countStatusCampaigns(CampaignStatus.PENDING));
        result.put("REJECTED", campaignRepository.countStatusCampaigns(CampaignStatus.REJECTED));
        result.put("PAUSED", campaignRepository.countStatusCampaigns(CampaignStatus.PAUSED));
        result.put("EXPIRED", campaignRepository.countStatusCampaigns(CampaignStatus.EXPIRED));
        return result;
    }

    public List<Campaign> getAllAdvertiserCampaigns(Long id) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            List<Campaign> result = campaignRepository.findCampaignsByAdvertiser(account.getAdvertisers().getId());
            return result;
        }
        return null;
    }


    public List<Campaign> getAllPublisherCampaigns(Long id) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            List<Campaign> result = campaignRepository.findCampaignsByAffiliate(account.getAdvertisers().getId());
            return result;
        }
        return null;
    }

    public List<Campaign> getUnregisteredApprovedCampaigns(Long accountId) {
        Long publisherId = publisherRepository.findPublisherIdByAccountId(accountId);
        System.out.println(publisherId + "*******");
        return campaignRepository.findUnregisteredApprovedCampaigns(publisherId);
    }
}
