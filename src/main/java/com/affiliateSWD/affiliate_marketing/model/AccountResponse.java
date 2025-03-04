package com.affiliateSWD.affiliate_marketing.model;

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

    private String managementLevel; // ADMIN
    private String companyName;     // PUBLISHER
    private String billingInfo;     // PUBLISHER
    private String paymentInfo;     // ADVERTISERS
    private String referralCode;    // ADVERTISERS
}


//package com.affiliateSWD.affiliate_marketing.model;
//
//import com.affiliateSWD.affiliate_marketing.entity.Account;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class AccountReponse extends Account {
//    String token;
//}
//
