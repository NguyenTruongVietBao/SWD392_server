package com.affiliateSWD.affiliate_marketing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@DiscriminatorValue("ADVERTISER") // Phân biệt kiểu dữ liệu
//@Table(name = "advertisers")

public class Advertisers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private Double accountBalance;

    private String billingInfo;

    @OneToOne
    @JoinColumn(name="account_id", nullable = false, unique = true)
    @JsonIgnore
    Account accountAdvertisers;
}
