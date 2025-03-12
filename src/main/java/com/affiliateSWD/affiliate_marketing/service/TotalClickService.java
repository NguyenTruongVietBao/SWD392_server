package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.TotalClicks;
import com.affiliateSWD.affiliate_marketing.respository.TotalClickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalClickService {
    @Autowired
    TotalClickRepository totalClickRepository;

    public void incrementClickCount(AffiliateLink affiliateLink) {
        TotalClicks totalClicks = totalClickRepository.findByAffiliateLinkTotal(affiliateLink)
                .orElse(new TotalClicks());
        totalClicks.setClickCount(totalClicks.getClickCount() == null? 1: totalClicks.getClickCount() + 1);
        totalClicks.setAffiliateLinkTotal(affiliateLink);
        totalClickRepository.save(totalClicks);
    }
}
