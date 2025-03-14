package com.affiliateSWD.affiliate_marketing.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.affiliateSWD.affiliate_marketing.enums.CampaignStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class CampaignRequest {
   // private Long id;

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

   @Enumerated(EnumType.STRING)
   private CampaignStatus status;
   
   private LocalDateTime createdAt;


}

