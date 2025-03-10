package com.affiliateSWD.affiliate_marketing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter

public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentInfo;

    private String referralCode;

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    Account accountPublisher;

    @OneToMany(mappedBy = "publisherFeedback", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "publisherDashboard", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<DashboardMatric> dashboardMatrics;

    @OneToMany(mappedBy = "publisherAffiliate", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<AffiliateLink> affiliateLinks;

    @OneToMany(mappedBy = "publisherPayout", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Payout> payouts;
}
