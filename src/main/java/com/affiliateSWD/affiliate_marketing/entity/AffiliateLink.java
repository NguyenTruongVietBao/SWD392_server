package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.AffiliateStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter

public class AffiliateLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingUrl;

    private String shortUrl;

    private AffiliateStatus status;

    private LocalDateTime createAt;

    private String approveAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisherAffiliate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaignAffiliate;

    @OneToMany(mappedBy = "affiliateLinkTotal", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<TotalClicks> totalClicks;

    @OneToMany(mappedBy = "affiliateLinkClick", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Clicks> clicks;

    @OneToMany(mappedBy = "affiliateLinkTransaction", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Transaction> transactions;

    @OneToMany(mappedBy = "affiliateLinkFraud", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<FraudDetection> fraudDetections;
}
