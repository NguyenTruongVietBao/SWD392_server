package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.respository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Account> getPublishersByCampaignId(Long campaignId) {
        List<Publisher> publishers = publisherRepository.findAllPublishersByCampaignId(campaignId);

        // Trả về danh sách Account từ Publisher
        return publishers.stream()
                .map(Publisher::getAccountPublisher) // Truyền ra Account từ Publisher
                .collect(Collectors.toList());
    }
}
