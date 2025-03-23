package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Advertisers;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.enums.AccountStatus;
import com.affiliateSWD.affiliate_marketing.model.Response.AccountResponse;
import com.affiliateSWD.affiliate_marketing.model.request.AdvertiserRegisterRequest;
import com.affiliateSWD.affiliate_marketing.model.request.LoginRequest;
import com.affiliateSWD.affiliate_marketing.model.request.PublisherRegisterRequest;
import com.affiliateSWD.affiliate_marketing.respository.AuthenticationRepository;
import com.affiliateSWD.affiliate_marketing.respository.PublisherRepository;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import com.affiliateSWD.affiliate_marketing.config.SecurityConfig;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional

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
    PublisherRepository publisherRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    AccountUtils accountUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findByUsername(username);
    }
    public Account findByUsername(String username) {
        return authenticationRepository.findByUsername(username);
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));             
            if(loginRequest == null || loginRequest.getPassword() == null || loginRequest.getUsername() == null){
                throw new BadCredentialsException("Bad request");
            }
            Account account = authenticationRepository.findByUsername(loginRequest.getUsername());
            if (account == null || !securityConfig.passwordEncoder().matches(loginRequest.getPassword(), account.getPassword())) {
                throw new BadCredentialsException("Incorrect username or password");
            }
            if(!account.getStatus().equals(AccountStatus.ACTIVE)){
                throw new AuthenticationServiceException("Your account locked!!!");
            }
            AccountResponse accountReponse = new AccountResponse();
            String token = tokenService.generateToken(account);

            accountReponse.setUsername(account.getUsername());
            accountReponse.setId(account.getId());
            accountReponse.setEmail(account.getEmail());
            accountReponse.setPhoneNumber(account.getPhoneNumber());
            accountReponse.setRole(account.getRole());
            accountReponse.setStatus(account.getStatus());
            accountReponse.setToken(token);

            if (account.getRole().equals(AccountRoles.PUBLISHER) && account.getPublisher() != null){
                accountReponse.setPaymentInfo(account.getPublisher().getPaymentInfo());
                accountReponse.setReferralCode(account.getPublisher().getReferralCode());
            } else if (account.getRole().equals(AccountRoles.ADVERTISERS) && account.getAdvertisers() != null){
                accountReponse.setBillingInfo(account.getAdvertisers().getBillingInfo());
                accountReponse.setCompanyName(account.getAdvertisers().getCompanyName());
                accountReponse.setAccountBalance(account.getAdvertisers().getAccountBalance());
            }
            return accountReponse;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        }
    }

    public AccountResponse loginWithToken() {
        Account account=  authenticationRepository.findAccountById(accountUtils.getAccountCurrent().getId());
        System.out.println(account.getId());
        if(!account.getStatus().equals(AccountStatus.ACTIVE)){
            throw new AuthenticationServiceException("Your account locked!!!");
        }
        AccountResponse accountReponse = new AccountResponse();
        String token = tokenService.generateToken(account);
        accountReponse.setUsername(account.getUsername());
        accountReponse.setId(account.getId());
        accountReponse.setEmail(account.getEmail());
        accountReponse.setPhoneNumber(account.getPhoneNumber());
        accountReponse.setRole(account.getRole());
        accountReponse.setStatus(account.getStatus());
        accountReponse.setToken(token);
        if (account.getRole().equals(AccountRoles.PUBLISHER) && account.getPublisher() != null){
            accountReponse.setPaymentInfo(account.getPublisher().getPaymentInfo());
            accountReponse.setReferralCode(account.getPublisher().getReferralCode());
        } else if (account.getRole().equals(AccountRoles.ADVERTISERS) && account.getAdvertisers() != null){
            accountReponse.setBillingInfo(account.getAdvertisers().getBillingInfo());
            accountReponse.setCompanyName(account.getAdvertisers().getCompanyName());
            accountReponse.setAccountBalance(account.getAdvertisers().getAccountBalance());
        }
        return accountReponse;
    }

    public Account registerPublisher(PublisherRegisterRequest registerRequest) throws AuthenticationServiceException{
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setEmail(registerRequest.getEmail());
        account.setPhoneNumber(registerRequest.getPhoneNumber());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setRole(AccountRoles.PUBLISHER);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());

        Publisher publisher = new Publisher();
        publisher.setPaymentInfo(registerRequest.getPaymentInfo());
        publisher.setReferralCode(registerRequest.getReferralCode());
        publisher.setAccountPublisher(account);
        account.setPublisher(publisher);
        return authenticationRepository.save(account);
    }

    public Account registerAdvertisers(AdvertiserRegisterRequest registerRequest) throws AuthenticationServiceException{
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setEmail(registerRequest.getEmail());
        account.setPhoneNumber(registerRequest.getPhoneNumber());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setRole(AccountRoles.ADVERTISERS);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());

        Advertisers advertisers = new Advertisers();
        advertisers.setBillingInfo(registerRequest.getBillingInfo());
        advertisers.setCompanyName(registerRequest.getCompanyName());
        advertisers.setAccountBalance(0.0);
        advertisers.setAccountAdvertisers(account);
        account.setAdvertisers(advertisers);

        return authenticationRepository.save(account);
    }
    

    public List<Account> getAccountsByRole(AccountRoles role) {
        List<Account> accounts = authenticationRepository.findByRole(role);
        return accounts;
    }

    public  Account test(){
        return accountUtils.getAccountCurrent();
    }

    public Map<String, Long> getStats() {
        Map<String, Long> result = new HashMap<>();
        result.put("totalAccounts", authenticationRepository.countTotalAccounts());
        result.put("totalPublishers", authenticationRepository.countTotalPublishers());
        result.put("totalAdvertisers", authenticationRepository.countTotalAdvertisers());
        return result;
    }

    public boolean deleteAccount(Long accountId) {
        try {
            Optional<Account> account = authenticationRepository.findById(accountId);
            if (account.isPresent()) {
                authenticationRepository.delete(account.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting account: " + e.getMessage());
        }
    }

    public Account updatePublisher(Long id, PublisherRegisterRequest updateRequest) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setEmail(updateRequest.getEmail().trim() != ""? updateRequest.getEmail():account.getEmail());
            account.setPhoneNumber(updateRequest.getPhoneNumber().trim() != ""? updateRequest.getPhoneNumber():account.getPhoneNumber());
            account.setPassword(updateRequest.getPassword().trim() != ""? updateRequest.getPassword(): account.getPassword());

            Publisher publisher = account.getPublisher();

            publisher.setPaymentInfo(updateRequest.getPaymentInfo().trim() != ""? updateRequest.getPaymentInfo(): account.getPublisher().getPaymentInfo());
            publisher.setReferralCode(updateRequest.getReferralCode().trim() != ""? updateRequest.getReferralCode(): account.getPublisher().getReferralCode());
            account.setPublisher(publisher);

            return authenticationRepository.save(account);
        }
        return null;
    }

    public Account updateAdvertiser(Long id, AdvertiserRegisterRequest updateRequest) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setEmail(updateRequest.getEmail().trim() != ""? updateRequest.getEmail():account.getEmail());
            account.setPhoneNumber(updateRequest.getPhoneNumber().trim() != ""? updateRequest.getPhoneNumber():account.getPhoneNumber());
            account.setPassword(updateRequest.getPassword().trim() != ""? updateRequest.getPassword(): account.getPassword());

            Advertisers advertiser = account.getAdvertisers();

            advertiser.setCompanyName(updateRequest.getCompanyName().trim() != ""? updateRequest.getCompanyName(): account.getAdvertisers().getCompanyName());
            advertiser.setBillingInfo(updateRequest.getBillingInfo().trim() != ""? updateRequest.getBillingInfo(): account.getAdvertisers().getBillingInfo());
            account.setAdvertisers(advertiser);

            return authenticationRepository.save(account);
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        return authenticationRepository.findAll();
    }

    public List<Account> getRecentAccounts() {
        return authenticationRepository.findTop4ByOrderByCreatedAtDesc();
    }

    public Account changeStatus(Long id, AccountStatus status) {
        Optional<Account> accountOptional = authenticationRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setStatus(status);
            return authenticationRepository.save(account);
        }
        return null;
    }
}