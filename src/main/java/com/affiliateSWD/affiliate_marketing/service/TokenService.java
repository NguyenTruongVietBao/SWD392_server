package com.affiliateSWD.affiliate_marketing.service;

import com.affiliateSWD.affiliate_marketing.entity.Account;
import com.affiliateSWD.affiliate_marketing.enums.AccountRoles;
import com.affiliateSWD.affiliate_marketing.respository.AuthenticationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    private final String SECRET_KEY = "HT4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ Tạo token với role và các thông tin khác
    public String generateToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());  // 🔥 Lưu role vào token (dưới dạng String)

        return Jwts.builder()
                .claims(claims)
                .subject(account.getUsername()) // Chủ sở hữu của token
                .issuedAt(new Date(System.currentTimeMillis())) // Thời gian phát hành
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // Hết hạn sau 24h
                .signWith(getSigninKey()) // Ký token với secret key
                .compact();
    }

    // ✅ Giải mã token và lấy tất cả claims
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ Lấy username từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Lấy role từ token (chuyển String thành Enum)
    public AccountRoles extractRole(String token) {
        String roleString = extractClaim(token, claims -> claims.get("role", String.class));
        return AccountRoles.valueOf(roleString);
    }

    // ✅ Lấy Account từ token (bao gồm role)
    public Account extractAccount(String token) {
        String username = extractUsername(token);
        Account account = authenticationRepository.findByUsername(username);

        // Cập nhật role từ token nếu cần
        if (account != null) {
            account.setRole(extractRole(token));
        }

        return account;
    }

    // ✅ Kiểm tra token hết hạn chưa
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Lấy ngày hết hạn từ token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Lấy claim cụ thể từ token
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
}
