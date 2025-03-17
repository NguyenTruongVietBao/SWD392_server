package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.*;
import com.affiliateSWD.affiliate_marketing.enums.ActionType;
import com.affiliateSWD.affiliate_marketing.enums.ClickStatus;
import com.affiliateSWD.affiliate_marketing.enums.TransactionStatus;
import com.affiliateSWD.affiliate_marketing.respository.CampaignRepository;
import com.affiliateSWD.affiliate_marketing.respository.ClickTrackingRepository;
import com.affiliateSWD.affiliate_marketing.respository.PayoutRepository;
import com.affiliateSWD.affiliate_marketing.respository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Transaction createTransaction(AffiliateLink affiliateLink, HttpServletRequest request) {
        String ipAddress = getClientIp(request);

        Clicks click = new Clicks();
        click.setAffiliateLinkClick(affiliateLink);
        click.setIpAddress(getClientIp(request));
        click.setSourceLabel(getSourceLabel(request));
        click.setQuanlityScore(evaluateQualityScore(request));
        click.setTimeClick(LocalDateTime.now());
        click.setStatus(determineStatus(click.getQuanlityScore()));

        click = clickTrackingRepository.save(click);

        Optional<Clicks> existingClick = clickTrackingRepository.findByAffiliateLinkClickAndIpAddress(affiliateLink, ipAddress);

        if (existingClick.isPresent() && existingClick.get().getTransactionClick() != null) {
            return existingClick.get().getTransactionClick(); // Trả về transaction đã tồn tại
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
    }

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For"); // Nếu request đi qua proxy
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr(); // Lấy IP gốc của user
        }
        return ip;
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
