package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.PayoutStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter

public class Payout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime payoutDate;

    private BigDecimal amount;

    private PayoutStatus status;

    @OneToMany(mappedBy = "payoutTransaction", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin adminPayout;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisherPayout;

    @ManyToOne
    @JoinColumn(name = "advertiser_id", nullable = false)
    private Advertisers advertisersPayout;
}
