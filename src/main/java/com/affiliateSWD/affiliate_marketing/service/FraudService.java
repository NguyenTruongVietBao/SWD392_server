package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.enums.ClickStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service

public class FraudService {

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
