package com.affiliateSWD.affiliate_marketing.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.affiliateSWD.affiliate_marketing.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAccountAdminId(Long accountId);
}


