package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.ClickStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter

public class Clicks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    private String conversions;

    private String sourceLabel;

    private float quanlityScore;

    private ClickStatus status;

    private LocalDateTime timeClick;

    @ManyToOne
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateLink affiliateLinkClick;

    @ManyToOne
    @JoinColumn(name = "click_id", nullable = false)
    private Transaction transactionClick;

    @OneToOne(mappedBy = "clickFraud", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<FraudDetection> fraudDetections;

}
