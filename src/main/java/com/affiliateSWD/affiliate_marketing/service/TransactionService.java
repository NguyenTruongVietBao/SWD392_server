package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.*;
import com.affiliateSWD.affiliate_marketing.enums.ActionType;
import com.affiliateSWD.affiliate_marketing.enums.ClickStatus;
import com.affiliateSWD.affiliate_marketing.enums.FraudStatus;
import com.affiliateSWD.affiliate_marketing.enums.TransactionStatus;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
import com.affiliateSWD.affiliate_marketing.respository.ClickTrackingRepository;
import com.affiliateSWD.affiliate_marketing.respository.FraudDetectionRepository;
import com.affiliateSWD.affiliate_marketing.respository.PayoutRepository;
import com.affiliateSWD.affiliate_marketing.respository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private FraudDetectionRepository fraudDetectionRepository;

    @Autowired
    private PayoutRepository payoutRepository;

    @Autowired
    private ClickTrackingRepository clickTrackingRepository;
    @Transactional
    public Transaction createTransaction(AffiliateLink affiliateLink, HttpServletRequest request) {
        try {
        String ipAddress = getClientIp(request);

        Clicks click = new Clicks();
        click.setAffiliateLinkClick(affiliateLink);
        click.setIpAddress(getClientIp(request));
        click.setSourceLabel(getSourceLabel(request));
        click.setQuanlityScore(evaluateQualityScore(request));
        click.setTimeClick(LocalDateTime.now());
        click.setStatus(determineStatus(click.getQuanlityScore()));

        click = clickTrackingRepository.save(click);
        Optional<Clicks> existingClick = clickTrackingRepository.findFirstByAffiliateLinkClickAndIpAddress(affiliateLink, ipAddress);


        Long affiliateId = affiliateLink.getId();
        LocalDateTime fiveMinutesAgo = click.getTimeClick().minusMinutes(5);
        List<Clicks> recentClicks = clickTrackingRepository.findRecentClicks(affiliateId, ipAddress, fiveMinutesAgo);
        LocalDateTime date = LocalDateTime.now();
        if (recentClicks.size() >= 3) { 
            System.out.println("Fraud detected: More than 3 clicks within 5 minutes.");
            FraudDetection fraudDetection = new FraudDetection();
            fraudDetection.setAffiliateLinkFraud(affiliateLink);
            fraudDetection.setFlaggedDate(date);
            fraudDetection.setClickFraud(click);
            fraudDetection.setReason("More than 3 clicks within 5 minutes.");    
            fraudDetection.setStatus(FraudStatus.PENDING);
            click.setStatus(ClickStatus.FRAUD);
            clickTrackingRepository.save(click);
            fraudDetectionRepository.save(fraudDetection);                 
        }

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


    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // Chuyển IPv6 localhost về IPv4
        if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "::1".equals(ipAddress)) {
            ipAddress = "127.0.0.1";
        }

        return ipAddress;
    }

    public String getSourceLabel(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer == null) return "Direct"; // Truy cập trực tiếp
        if (referrer.contains("facebook.com")) return "Facebook Ads";
        if (referrer.contains("google.com")) return "Google Search";
        return "Other";
    }

    public float evaluateQualityScore(HttpServletRequest request) {
        int score = 100;
        if (getClientIp(request).startsWith("192.168")) score -= 30; // Giảm điểm nếu là IP nội bộ
        if ("Bot".equals(getSourceLabel(request))) score -= 50; // Click bot giảm điểm
        return score;
    }

    public ClickStatus determineStatus(float qualityScore) {
        return (qualityScore > 50) ? ClickStatus.VALID : ClickStatus.FRAUD; // Click hợp lệ nếu score > 50
    }
}
