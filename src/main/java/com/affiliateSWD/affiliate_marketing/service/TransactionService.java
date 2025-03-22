package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.*;
import com.affiliateSWD.affiliate_marketing.enums.ActionType;
import com.affiliateSWD.affiliate_marketing.enums.TransactionStatus;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
import com.affiliateSWD.affiliate_marketing.respository.ClickTrackingRepository;
import com.affiliateSWD.affiliate_marketing.respository.PayoutRepository;
import com.affiliateSWD.affiliate_marketing.respository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PayoutRepository payoutRepository;

    @Autowired
    private ClickTrackingRepository clickTrackingRepository;

    @Autowired
    private FraudService fraudService;

    @Transactional
    public Transaction createTransaction(AffiliateLink affiliateLink, HttpServletRequest request) {
        try {
        String ipAddress = fraudService.getClientIp(request);

        Clicks click = new Clicks();
        click.setAffiliateLinkClick(affiliateLink);
        click.setIpAddress(fraudService.getClientIp(request));
        click.setSourceLabel(fraudService.getSourceLabel(request));
        click.setQuanlityScore(fraudService.evaluateQualityScore(request));
        click.setTimeClick(LocalDateTime.now());
        click.setStatus(fraudService.determineStatus(click.getQuanlityScore()));

        click = clickTrackingRepository.save(click);
        Optional<Clicks> existingClick = clickTrackingRepository.findFirstByAffiliateLinkClickAndIpAddress(affiliateLink, ipAddress);

        if (existingClick.isPresent() && existingClick.get().getTransactionClick() != null) {
            Transaction existingTransaction = existingClick.get().getTransactionClick();
            click.setTransactionClick(existingTransaction);
            clickTrackingRepository.save(click); 
            return existingTransaction;
        }

        Transaction transaction = new Transaction();

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

        transactionRepository.save(transaction);

        click.setTransactionClick(transaction);
        clickTrackingRepository.save(click);

        return transaction;
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Lỗi xảy ra khi truy vấn: " + e.getMessage());
    }
    return null;
    }

}
