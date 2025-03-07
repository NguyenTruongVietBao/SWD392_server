package com.affiliateSWD.affiliate_marketing.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Setter
@Getter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title ;
    private BigDecimal budget;
    private String description;
    private String targetAudience ;

    private float commissionRate;
    private BigDecimal commissionValue;

    private float rating;
    private String adsLink;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate  endDate;
    private String status;
    
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
    
    @ManyToOne
    @JoinColumn(name = "advertisers_id", nullable = false)
    private Advertisers advertisers;

}
