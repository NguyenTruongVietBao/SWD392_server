package com.affiliateSWD.affiliate_marketing.Controller;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.enums.AccountStatus;
import com.affiliateSWD.affiliate_marketing.model.request.AdvertiserRegisterRequest;
import com.affiliateSWD.affiliate_marketing.model.request.LoginRequest;
import com.affiliateSWD.affiliate_marketing.model.request.PublisherRegisterRequest;
import com.affiliateSWD.affiliate_marketing.model.Response.AccountResponse;
import com.affiliateSWD.affiliate_marketing.service.AuthenticationService;
import com.affiliateSWD.affiliate_marketing.utils.AccountUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/accounts")
@SecurityRequirement(name = "bearerAuth")


public class AccountsController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountUtils accountUtils;
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
        AccountResponse users = authenticationService.login(loginRequest);
        return ResponseEntity.ok(users);
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap("message", e.getMessage()));
    }
}

    @PostMapping("/loginWithToken")
    public ResponseEntity loginWithToken() {
        AccountResponse account = authenticationService.loginWithToken();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/register/publisher")
    public ResponseEntity registerPublisher(@RequestBody PublisherRegisterRequest registerRequest) {
        Account account = authenticationService.registerPublisher(registerRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/register/advertiser")
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
        return ResponseEntity.ok(accounts);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = authenticationService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        Account account = authenticationService.test();
        return ResponseEntity.ok(account);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getAllPublisherCampaign() {
        Map<String, Long> total = authenticationService.getStats();
        return ResponseEntity.ok(total);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            boolean isDeleted = authenticationService.deleteAccount(id);
            if (isDeleted) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Account deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Account not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An error occurred: " + e.getMessage()));
        }
    }

    @PutMapping("/update/publisher/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @RequestBody PublisherRegisterRequest updateRequest) {
        try {
            Account updatedAccount = authenticationService.updatePublisher(id, updateRequest);
            if (updatedAccount != null) {
                return ResponseEntity.ok(updatedAccount);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Account not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Error updating account: " + e.getMessage()));
        }
    }

    @PutMapping("/update/advertiser/{id}")
    public ResponseEntity<?> updateAdvertiser(@PathVariable Long id, @RequestBody AdvertiserRegisterRequest updateRequest) {
        try {
            Account updatedAccount = authenticationService.updateAdvertiser(id, updateRequest);
            if (updatedAccount != null) {
                return ResponseEntity.ok(updatedAccount);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Account not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Error updating account: " + e.getMessage()));
        }
    }

//    @GetMapping("")
//    public ResponseEntity<List<Account>> getAllAccounts() {
//        List<Account> accounts = authenticationService.getAllAccounts();
//        return ResponseEntity.ok(accounts);
//    }

    @GetMapping("/recentAccounts")
    public ResponseEntity<List<Account>> getRecentAccounts() {
        List<Account> accounts = authenticationService.getRecentAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/changeStatus/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, AccountStatus status) {
        try {
            Account account = authenticationService.changeStatus(id, status);
            if (account != null) {
                return ResponseEntity.ok(account);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Account not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Error change status: " + e.getMessage()));
        }
    }
}
