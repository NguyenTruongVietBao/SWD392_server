package com.affiliateSWD.affiliate_marketing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dummyField = "default";

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="account_id")
    Account accountAdmin;

    @OneToMany(mappedBy = "adminCampaign", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Campaign> campaigns;
    //
    @OneToMany(mappedBy = "adminPayout", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Payout> payouts;

    @OneToMany(mappedBy = "adminFraud", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<FraudDetection> fraudDetections;
}

