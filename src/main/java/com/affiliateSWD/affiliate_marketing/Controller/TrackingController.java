package com.affiliateSWD.affiliate_marketing.Controller;

import com.affiliateSWD.affiliate_marketing.entity.Campaign;
import com.affiliateSWD.affiliate_marketing.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")

public class TrackingController {

    @Autowired
    TrackingService trackingService;

    @GetMapping("/{totalClicks}/{aff_id}")
    public long getTotalClicks(@PathVariable Long aff_id) {
        return trackingService.getClicks(aff_id);
    }

//    @GetMapping("/{totalClicks}/{aff_id}")
//    public long getTotalClicks(@PathVariable Long aff_id) {
//        return trackingService.getClicks(aff_id);
//    }
}
