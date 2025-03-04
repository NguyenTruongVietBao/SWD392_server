package com.affiliateSWD.affiliate_marketing.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherRegisterRequest {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String paymentInfo;
    private String referralCode;
}
