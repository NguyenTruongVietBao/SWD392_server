package com.affiliateSWD.affiliate_marketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter

public class DashboardMatric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalClicks;

    private Long totalConversions;

    private BigDecimal totalEarnings;

    private BigDecimal totalCost;

    private BigDecimal totalRevenue;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaignDashboard;

    @ManyToOne
    @JoinColumn(name = "advertiser_id", nullable = false)
    private Advertisers advertisersDashboard;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisherDashboard;
}
