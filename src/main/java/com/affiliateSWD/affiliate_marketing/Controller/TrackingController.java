package com.affiliateSWD.affiliate_marketing.Controller;

import com.affiliateSWD.affiliate_marketing.entity.AffiliateLink;
import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import com.affiliateSWD.affiliate_marketing.service.ClickTrackingService;
import com.affiliateSWD.affiliate_marketing.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tracking")

public class TrackingController {

    @Autowired
    TrackingService trackingService;

    @Autowired
    ClickTrackingService clickTrackingService;

    @GetMapping("/{totalClicks}/{aff_id}")
    public long getTotalClicks(@PathVariable Long aff_id) {
        return trackingService.getClicks(aff_id);
    }
     @GetMapping("/check")
    public Optional<Clicks> checkClick(@RequestParam Long affiliateLinkId, @RequestParam String ipAddress) {
        AffiliateLink affiliateLink = new AffiliateLink();
        affiliateLink.setId(affiliateLinkId); // Tạo một đối tượng giả lập, nên kiểm tra trong DB
        return clickTrackingService.checkClick(affiliateLink, ipAddress);
    }

//    @GetMapping("/{totalClicks}/{aff_id}")
//    public long getTotalClicks(@PathVariable Long aff_id) {
//        return trackingService.getClicks(aff_id);
//    }
}
