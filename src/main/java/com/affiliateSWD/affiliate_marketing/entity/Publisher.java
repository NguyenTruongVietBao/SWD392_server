package com.affiliateSWD.affiliate_marketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

   
}


