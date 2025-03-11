package com.affiliateSWD.affiliate_marketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter

public class TotalClicks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clickCount;

    @ManyToOne
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateLink affiliateLinkTotal;
}
