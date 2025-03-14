package com.affiliateSWD.affiliate_marketing.model.Response;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private AccountRoles role;
    private AccountStatus status;
    private String token;

  
    private String companyName;
    private String billingInfo;
    private Double accountBalance;

    
    private String paymentInfo;
    private String referralCode;


    public AccountResponse(Account account, String token) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phoneNumber = account.getPhoneNumber();
        this.role = account.getRole();
        this.token = token;


        if (account.getAdvertisers() != null) {
            this.companyName = account.getAdvertisers().getCompanyName();
            this.billingInfo = account.getAdvertisers().getBillingInfo();
            this.accountBalance = account.getAdvertisers().getAccountBalance();
        }

   
        if (account.getPublisher() != null) {
            this.paymentInfo = account.getPublisher().getPaymentInfo();
            this.referralCode = account.getPublisher().getReferralCode();
        }
    }
}
