package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.FraudStatus;
import com.affiliateSWD.affiliate_marketing.enums.FraudType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class FraudDetection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private FraudStatus status;

    private String reason;

    private String evidence;

    private FraudType fraudType;

    private LocalDateTime flaggedDate;

    private LocalDateTime reviewAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin adminFraud;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateLink affiliateLinkFraud;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "click_id", nullable = false)
    private Clicks clickFraud;

}
