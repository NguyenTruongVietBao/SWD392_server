package com.affiliateSWD.affiliate_marketing.entity;

import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
//@Table(name = "accounts")
//@Inheritance(strategy = InheritanceType.JOINED) // Dùng Joined Table để kế thừa

public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    private AccountRoles role;

//    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToOne(mappedBy = "accountPublisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Publisher publisher;

    @JsonIgnore
    @OneToOne(mappedBy = "accountAdvertisers", cascade = CascadeType.ALL, orphanRemoval = true)
    private Advertisers advertisers;

    @JsonIgnore
    @OneToOne(mappedBy = "accountAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
    private Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
