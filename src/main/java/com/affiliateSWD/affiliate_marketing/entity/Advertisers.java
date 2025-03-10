package com.affiliateSWD.affiliate_marketing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter

public class Advertisers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private Double accountBalance;

    private String billingInfo;

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    Account accountAdvertisers;

    @OneToMany(mappedBy = "advertisersCampaign", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Campaign> campaigns;

    @OneToMany(mappedBy = "advertisersDashboard", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<DashboardMatric> dashboardMatrics;

    @OneToMany(mappedBy = "advertisersTransaction", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Transaction> transactions;

    @OneToMany(mappedBy = "advertisersPayout", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Payout> payouts;
}
