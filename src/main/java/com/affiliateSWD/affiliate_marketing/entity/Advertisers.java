package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Advertisers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private Double accountBalance;
    private String billingInfo;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public void setUsername(String username) {
        account.setUsername(username);
    }

    public void setEmail(String email) {
        account.setEmail(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        account.setPhoneNumber(phoneNumber);
    }

    public void setPassword(String encode) {
        account.setPassword(encode);
    }

    public void setStatus(AccountStatus accountStatus) {
        account.setStatus(accountStatus);
    }

    public void setCompanyName(String companyName) {
        this.setCompanyName(companyName);
    }

    public void setBillingInfo(String billingInfo) {
        this.setBillingInfo(billingInfo);
    }

    public void setRole(AccountRoles accountRoles) {
        account.setRole(accountRoles);
    }

}


//package com.affiliateSWD.affiliate_marketing.entity;
//
//import jakarta.persistence.Entity;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//
//public class Advertisers extends Account {
//    String companyName;
//    Double accountBalance;
//    String billingInfo;
//}
