package com.affiliateSWD.affiliate_marketing.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.affiliateSWD.affiliate_marketing.entity.Advertisers;

public interface AdvertisersRepository extends JpaRepository<Advertisers, Long> {
    Advertisers findByAccountAdvertisersId(Long accountId);

}
