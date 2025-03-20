package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import com.affiliateSWD.affiliate_marketing.entity.Transaction;
import com.affiliateSWD.affiliate_marketing.enums.ClickStatus;
import com.affiliateSWD.affiliate_marketing.respository.ClickTrackingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class ClickTrackingService {
    @Autowired
    ClickTrackingRepository clickTrackingRepository;


    public Optional<Clicks> checkClick(AffiliateLink affiliateLink, String ipAddress) {
        return clickTrackingRepository.findFirstByAffiliateLinkClickAndIpAddress(affiliateLink, ipAddress);
    }

//    public Clicks createClick(AffiliateLink affiliateLink, HttpServletRequest request, Transaction transaction) {
//        Clicks click = new Clicks();
//        click.setAffiliateLinkClick(affiliateLink);
//        click.setIpAddress(getClientIp(request));
//        click.setSourceLabel(getSourceLabel(request));
//        click.setQuanlityScore(evaluateQualityScore(request));
//        click.setTimeClick(LocalDateTime.now());
//        click.setStatus(determineStatus(click.getQuanlityScore()));
//        click.setTransactionClick(transaction);
//        return clickTrackingRepository.save(click);
//    }
//
//    public String getClientIp(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For"); // Nếu request đi qua proxy
//        if (ip == null || ip.isEmpty()) {
//            ip = request.getRemoteAddr(); // Lấy IP gốc của user
//        }
//        return ip;
//    }
//
//    public String getSourceLabel(HttpServletRequest request) {
//        String referrer = request.getHeader("Referer");
//        if (referrer == null) return "Direct"; // Truy cập trực tiếp
//        if (referrer.contains("facebook.com")) return "Facebook Ads";
//        if (referrer.contains("google.com")) return "Google Search";
//        return "Other";
//    }
//
//    public float evaluateQualityScore(HttpServletRequest request) {
//        int score = 100;
//        if (getClientIp(request).startsWith("192.168")) score -= 30; // Giảm điểm nếu là IP nội bộ
//        if ("Bot".equals(getSourceLabel(request))) score -= 50; // Click bot giảm điểm
//        return score;
//    }
//
//    public ClickStatus determineStatus(float qualityScore) {
//        return (qualityScore > 50) ? ClickStatus.VALID : ClickStatus.FRAUD; // Click hợp lệ nếu score > 50
//    }
}
