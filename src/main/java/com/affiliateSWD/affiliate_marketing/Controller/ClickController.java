package com.affiliateSWD.affiliate_marketing.Controller;

import com.affiliateSWD.affiliate_marketing.entity.Clicks;
import com.affiliateSWD.affiliate_marketing.service.ClickTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/click")

public class ClickController {

    @Autowired
    private ClickTrackingService clickTrackingService;
    @GetMapping("/clickAffiliate/{accountId}")
    public List<Clicks> getClicksByPublisherId(@PathVariable Long accountId) {
        return clickTrackingService.getClicksByPublisherId(accountId);
    }
}
