package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface PublisherRepository extends JpaRepository<Publisher, Long>
{
    Optional<Publisher> findByAccountPublisherId(Long accountId);
}