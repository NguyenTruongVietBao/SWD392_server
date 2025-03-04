package com.affiliateSWD.affiliate_marketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String managementLevel;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
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
//public class Admin extends Account {
//
//}
