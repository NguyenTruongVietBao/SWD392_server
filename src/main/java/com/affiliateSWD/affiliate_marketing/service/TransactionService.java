package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.entity.TotalClicks;
import com.affiliateSWD.affiliate_marketing.entity.Transaction;
import com.affiliateSWD.affiliate_marketing.enums.ActionType;
import com.affiliateSWD.affiliate_marketing.enums.TransactionStatus;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
import com.affiliateSWD.affiliate_marketing.respository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service

public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    public Transaction createTransaction(AffiliateLink affiliateLink) {
        Transaction transaction = transactionRepository.findByAffiliateLinkTransaction(affiliateLink)
                .orElse(new Transaction());
        Campaign campaign = affiliateLink.getCampaignAffiliate();
        transaction.setActionType(ActionType.CLICK);
        transaction.setAmount(campaign.getCommissionValue());
        transaction.setAdvertiserCost(campaign.getCommissionValue());
        float commissionEarned = campaign.getCommissionValue() * campaign.getCommissionRate();
        transaction.setCommissionEarned(commissionEarned);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreateAt(LocalDateTime.now());
        transaction.setAffiliateLinkTransaction(affiliateLink);
        transaction.setAdvertisersTransaction(campaign.getAdvertisersCampaign());
        return transactionRepository.save(transaction);
    }
}
