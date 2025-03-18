package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.respository.TotalClickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service

public class TrackingService {

    @Autowired
    TotalClickRepository totalClickRepository;

    public long getClicks(Long aff_id) throws NoSuchElementException {
        return totalClickRepository.getTotalClicksByAffiliateLink(aff_id);
    }
}
