package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.ActionType;
import com.affiliateSWD.affiliate_marketing.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ActionType actionType;

    private BigDecimal amount;

    private BigDecimal advertiserCost;

    private BigDecimal commissionEarned;

    private TransactionStatus status;

    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateLink affiliateLinkTransaction;

    @ManyToOne
    @JoinColumn(name = "advertiser_ad", nullable = false)
    private Advertisers advertisersTransaction;

    @OneToMany(mappedBy = "transactionClick", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Clicks> clicks;

    @ManyToOne
    @JoinColumn(name = "payout_id", nullable = false)
    private Payout payoutTransaction;
}
