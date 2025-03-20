package com.affiliateSWD.affiliate_marketing.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.enums.AffiliateStatus;
import com.affiliateSWD.affiliate_marketing.enums.CampaignStatus;

import com.affiliateSWD.affiliate_marketing.model.request.CampaignRequest;
import com.affiliateSWD.affiliate_marketing.respository.AuthenticationRepository;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.affiliateSWD.affiliate_marketing.entity.Admin;
import com.affiliateSWD.affiliate_marketing.entity.Advertisers;
import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.respository.AdminRepository;
import com.affiliateSWD.affiliate_marketing.respository.AdvertisersRepository;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
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
        result.put("totalAccounts", campaignRepository.countTotalCampaigns());
        result.put("approvedCampaigns", campaignRepository.countApprovedCampaigns());
        result.put("pendingCampaigns", campaignRepository.countPendingCampaigns());
        return result;
    }

    public Map<String, List<Campaign>> getAllAdvertiserCampaigns(Long id) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Map<String, List<Campaign>> result = new HashMap<>();
            result.put("PENDING", campaignRepository.findCampaignsByAdvertiserAndStatus(account.getId(), CampaignStatus.PENDING));
            result.put("APPROVED", campaignRepository.findCampaignsByAdvertiserAndStatus(account.getId(), CampaignStatus.APPROVED));
            result.put("REJECTED", campaignRepository.findCampaignsByAdvertiserAndStatus(account.getId(), CampaignStatus.REJECTED));
            result.put("PAUSED", campaignRepository.findCampaignsByAdvertiserAndStatus(account.getId(), CampaignStatus.PAUSED));
            result.put("EXPIRED", campaignRepository.findCampaignsByAdvertiserAndStatus(account.getId(), CampaignStatus.EXPIRED));
            return result;
        }
        return null;
    }

    public Map<String, List<Campaign>> getAllPublisherCampaigns(Long id) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Map<String, List<Campaign>> result = new HashMap<>();
            result.put("ACTIVE", campaignRepository.findCampaignsByAffiliateStatus(AffiliateStatus.ACTIVE, account.getId()));
            result.put("LOCKED", campaignRepository.findCampaignsByAffiliateStatus(AffiliateStatus.LOCKED, account.getId()));
            return result;
        }
        return null;
    }
}
