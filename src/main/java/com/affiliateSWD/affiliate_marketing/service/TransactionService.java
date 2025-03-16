package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.*;
import com.affiliateSWD.affiliate_marketing.enums.ActionType;
import com.affiliateSWD.affiliate_marketing.enums.TransactionStatus;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
import com.affiliateSWD.affiliate_marketing.respository.PayoutRepository;
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

    @Autowired
    private PayoutRepository payoutRepository;

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
        Payout payout = new Payout();
        payout = payoutRepository.save(payout);
        transaction.setPayoutTransaction(payout);
        return transactionRepository.save(transaction);
    }
}
