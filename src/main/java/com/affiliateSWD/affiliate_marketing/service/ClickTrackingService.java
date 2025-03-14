package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import com.affiliateSWD.affiliate_marketing.enums.ClickStatus;
import com.affiliateSWD.affiliate_marketing.respository.ClickTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class ClickTrackingService {
    @Autowired
    ClickTrackingRepository clickTrackingRepository;

    public Clicks createClick(AffiliateLink affiliateLink) {
        Clicks click = new Clicks();
        click.setAffiliateLinkClick(affiliateLink);
        //    click.setIpAddress(getClientIP()); // Hàm lấy địa chỉ IP của user
        click.setTimeClick(LocalDateTime.now());
        click.setStatus(ClickStatus.VALID); // Bạn có thể thêm cơ chế kiểm tra gian lận ở đây
        return clickTrackingRepository.save(click);
    }
}
