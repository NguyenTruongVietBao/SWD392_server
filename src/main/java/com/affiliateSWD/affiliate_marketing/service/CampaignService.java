package com.affiliateSWD.affiliate_marketing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id).orElse(null);
    }

    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
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
}
