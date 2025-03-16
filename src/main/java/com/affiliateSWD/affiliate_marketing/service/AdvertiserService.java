package com.affiliateSWD.affiliate_marketing.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.affiliateSWD.affiliate_marketing.entity.Advertisers;
import com.affiliateSWD.affiliate_marketing.respository.AdvertisersRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.transaction.Transactional;

@Service
public class AdvertiserService {

    @Autowired
    private AdvertisersRepository advertisersRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    public AdvertiserService(AdvertisersRepository advertisersRepository) {
        this.advertisersRepository = advertisersRepository;
    }

    @Transactional
    public Map<String, String> processPayment(Long accountId, Double amount) throws StripeException {
        Advertisers advertiser = advertisersRepository.findByAccountAdvertisersId(accountId);
        if (advertiser == null) {
            throw new RuntimeException("Advertiser not found for account ID: " + accountId);
        }

        if (amount < 0 ) {
            throw new RuntimeException("please more than zero");
        }
        advertiser.setAccountBalance(advertiser.getAccountBalance() + amount);

       
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) (amount * 100)) // Đơn vị: cents
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Advertiser Payment")
                                                                .build()
                                                )
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .build();

        Session session = Session.create(params);



        
        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", session.getUrl());
        response.put("status", "pending"); // Đợi người dùng thanh toán
        return response;
    }
}
