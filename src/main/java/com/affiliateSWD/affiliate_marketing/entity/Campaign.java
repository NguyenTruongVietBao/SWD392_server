package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Setter
@Getter

public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title ;
    private BigDecimal budget;
    private String description;
    private String targetAudience ;

    private float commissionRate;
    private float commissionValue;

    private float rating;
    private String adsLink;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate  endDate;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;
    
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "admin_id", nullable = true)
    private Admin adminCampaign;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "advertisers_id", nullable = false)
    private Advertisers advertisersCampaign;

    @OneToMany(mappedBy = "campaignFeedback", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Feedback> feedback;

    @OneToMany(mappedBy = "campaignDashboard", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<DashboardMatric> dashboardMatrics;

    @OneToMany(mappedBy = "campaignAffiliate", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<AffiliateLink> affiliateLinks;
}
