package com.affiliateSWD.affiliate_marketing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@DiscriminatorValue("PUBLISHER") // Phân biệt kiểu dữ liệu
//@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentInfo;

    private String referralCode;

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    Account accountPublisher;
}
