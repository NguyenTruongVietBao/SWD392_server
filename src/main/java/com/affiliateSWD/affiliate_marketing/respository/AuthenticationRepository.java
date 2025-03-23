package com.affiliateSWD.affiliate_marketing.respository;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    Account findAccountById(long userid);

    List<Account> findAll();

    List<Account> findByRole(AccountRoles role);

    @Query("SELECT COUNT(a) FROM Account a")
    long countTotalAccounts();

    @Query("SELECT COUNT(a) FROM Account a WHERE a.role = 'PUBLISHER'")
    long countTotalPublishers();

    @Query("SELECT COUNT(a) FROM Account a WHERE a.role = 'ADVERTISERS'")
    long countTotalAdvertisers();

    @Query("SELECT a FROM Account a ORDER BY a.createdAt DESC")
    List<Account> findTop4ByOrderByCreatedAtDesc();
}

