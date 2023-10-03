package com.app.task.config;

import com.app.task.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private String JWT_SECRET = "17b6d16e074b61821c83d4c3f48e0f598d143a82d18f4642b1526ee90333c95c";

    private Long JWT_TOKEN_EXPIRATION_IN_MINUTES = 60 * 24L ;

    private Long JWT_REFRESH_TOKEN_EXPIRATION_IN_MINUTES = 7 * 60 * 24L;

    public Long extractId(String token) {
        String idString = extractClaim(token, Claims::getSubject);
        return !StringUtils.isEmpty(idString)
                ? Long.parseLong(idString)
                : null;
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role").toString());
    }

    public String generateToken(Account account) {
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", getRole(account));

        return generateJwtToken(roles, account, 1000 * 60 * JWT_TOKEN_EXPIRATION_IN_MINUTES);
    }

    public String generateRefreshToken(Account account) {
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", getRole(account));

        return generateJwtToken(roles, account, 1000 * 60 * JWT_REFRESH_TOKEN_EXPIRATION_IN_MINUTES);
    }

    public String generateJwtToken(
            Map<String, Object> extraClaims,
            Account account,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(account.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, Account account) {
        final Long accId = extractId(token);
        return Objects.equals(accId, account.getId()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private List<String> getRole(Account account) {
       return account.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
