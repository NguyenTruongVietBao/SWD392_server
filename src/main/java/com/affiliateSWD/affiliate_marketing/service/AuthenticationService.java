package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.enums.AccountStatus;
import com.affiliateSWD.affiliate_marketing.model.LoginRequest;
import com.affiliateSWD.affiliate_marketing.model.AccountResponse;
import com.affiliateSWD.affiliate_marketing.respository.AuthenticationRepository;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import com.affiliateSWD.affiliate_marketing.config.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    AccountUtils accountUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findByUsername(username);
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));

            Account account = authenticationRepository.findByUsername(loginRequest.getUsername());
            if (account == null || !securityConfig.passwordEncoder().matches(loginRequest.getPassword(), account.getPassword())) {
                throw new BadCredentialsException("Incorrect username or password");
            }
            if (!account.getStatus().equals(AccountStatus.ACTIVE)) {
                throw new AuthenticationServiceException("Your account is locked!");
            }

            // ✅ Tạo token chứa thông tin của user
            String token = tokenService.generateToken(account);

            // ✅ Đưa dữ liệu từ `Account` vào `AccountResponse`
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setId(account.getId());
            accountResponse.setUsername(account.getUsername());
            accountResponse.setEmail(account.getEmail());
            accountResponse.setPhoneNumber(account.getPhoneNumber());
            accountResponse.setRole(account.getRole());
            accountResponse.setStatus(account.getStatus());
            accountResponse.setToken(token);

            // ✅ Thêm thông tin riêng biệt theo role
            if (account.getAdminDetails() != null) {
                accountResponse.setManagementLevel(account.getAdminDetails().getManagementLevel());
            }
            if (account.getAdvertiserDetails() != null) {
                accountResponse.setCompanyName(account.getAdvertiserDetails().getCompanyName());
                accountResponse.setBillingInfo(account.getAdvertiserDetails().getBillingInfo());
            }
            if (account.getPublisherDetails() != null) {
                accountResponse.setPaymentInfo(account.getPublisherDetails().getPaymentInfo());
                accountResponse.setReferralCode(account.getPublisherDetails().getReferralCode());
            }
            // ✅ Log để kiểm tra dữ liệu trả về
            log.info("✅ User logged in: {}", accountResponse);
            System.out.println("✅ User logged in: " + accountResponse);
            return accountResponse;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        }
    }

    public List<Account> getAllAccount() {
        return authenticationRepository.findAll();
    }

}