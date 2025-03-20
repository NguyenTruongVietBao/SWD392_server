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

    @Enumerated(EnumType.STRING) 
    @Column(name = "status") 
    private ClickStatus status;

    private LocalDateTime timeClick;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateLink affiliateLinkClick;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = true)
    private Transaction transactionClick;

    @OneToOne(mappedBy = "clickFraud", cascade = CascadeType.ALL)
    @JsonIgnore
    FraudDetection fraudDetections;

}
