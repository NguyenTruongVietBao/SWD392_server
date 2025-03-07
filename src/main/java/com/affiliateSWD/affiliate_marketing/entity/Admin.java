package com.affiliateSWD.affiliate_marketing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@DiscriminatorValue("ADMIN") // Phân biệt kiểu dữ liệu
//@Table(name = "admins")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dummyField = "default";

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    Account accountAdmin;
}

