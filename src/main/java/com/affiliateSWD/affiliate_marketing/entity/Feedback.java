package com.affiliateSWD.affiliate_marketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaignFeedback;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisherFeedback;

}
