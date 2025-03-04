package com.affiliateSWD.affiliate_marketing.api;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.entity.Publisher;
import com.affiliateSWD.affiliate_marketing.model.AccountResponse;
import com.affiliateSWD.affiliate_marketing.model.LoginRequest;
import com.affiliateSWD.affiliate_marketing.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")

public class UsersAPI {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        AccountResponse users = authenticationService.login(loginRequest);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllPublishers() {
        List<Account> account = authenticationService.getAllAccount();
        return ResponseEntity.ok(account);
    }
}
