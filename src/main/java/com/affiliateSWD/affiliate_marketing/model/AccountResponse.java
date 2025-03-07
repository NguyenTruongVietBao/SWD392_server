package com.affiliateSWD.affiliate_marketing.model;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse extends Account {
    private String token;

//  For adv
    private String companyName;
    private String billingInfo;
    private Double accountBalance;
//  For pub
    private String paymentInfo;
    private String referralCode;
}

