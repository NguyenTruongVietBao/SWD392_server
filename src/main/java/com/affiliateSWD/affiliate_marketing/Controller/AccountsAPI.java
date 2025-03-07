package com.affiliateSWD.affiliate_marketing.Controller;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.model.AccountResponse;
import com.affiliateSWD.affiliate_marketing.model.AdvertiserRegisterRequest;
import com.affiliateSWD.affiliate_marketing.model.LoginRequest;
import com.affiliateSWD.affiliate_marketing.model.PublisherRegisterRequest;
import com.affiliateSWD.affiliate_marketing.service.AuthenticationService;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "accounts")

public class AccountsAPI {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountUtils accountUtils;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        AccountResponse users = authenticationService.login(loginRequest);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/loginWithToken")
    public ResponseEntity loginWithToken() {
        Account account = authenticationService.loginWithToken();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/register/publisher")
    public ResponseEntity registerPublisher(@RequestBody PublisherRegisterRequest registerRequest) {
        Account account = authenticationService.registerPublisher(registerRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/register/advertier")
   
 public ResponseEntity<?> registerAdvertier(@RequestBody AdvertiserRegisterRequest registerRequest) {
    try {
        Account account = authenticationService.registerAdvertisers(registerRequest);
        return ResponseEntity.ok(account);
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}


    @GetMapping("/role/{role}")
    public ResponseEntity<List<Account>> getByRole(@PathVariable AccountRoles role) {
        List<Account> accounts = authenticationService.getAccountsByRole(role);

        // In ra để kiểm tra dữ liệu
        System.out.println("GET API - Role: " + role + " -> Accounts: " + accounts);

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        Account account = authenticationService.test();
        return ResponseEntity.ok(account);
    }
}
