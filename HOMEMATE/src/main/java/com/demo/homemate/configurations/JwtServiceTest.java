package com.demo.homemate.configurations;

import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

/**
 * Tạm thời chưa xong đang nghiên cứu
 */
@Service
public class JwtServiceTest {

    private String JWT_SECRET = "17b6d16e074b61821c83d4c3f48e0f598d143a82d18f4642b1526ee90333c95c";

    private Long JWT_TOKEN_EXPIRATION_IN_MINUTES = 60 * 24L ;

    private Long JWT_REFRESH_TOKEN_EXPIRATION_IN_MINUTES = 7 * 60 * 24L;

//    public int extractId(String token) {
//        String idString = extractClaim(token, Claims::getSubject);
//        return idString.isEmpty() ? null : Integer.parseInt(idString);
//    }
//
//    public String extractRole(String token) {
//        return extractClaim(token, claims -> claims.get("role").toString());
//    }

    public String generateToken(Employee account) {
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", getRole(account));

        return generateJwtToken(roles, account, 1000 * 60 * JWT_TOKEN_EXPIRATION_IN_MINUTES);
    }

    public String generateToken(Admin account) {
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", getRole(account));

        return generateJwtToken(roles, account, 1000 * 60 * JWT_TOKEN_EXPIRATION_IN_MINUTES);
    }
    public String generateToken(Customer account) {
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", getRole(account));

        return generateJwtToken(roles, account, 1000 * 60 * JWT_TOKEN_EXPIRATION_IN_MINUTES);
    }

//    public String generateRefreshToken(Account account) {
//        Map<String, Object> roles = new HashMap<>();
//        roles.put("role", getRole(account));
//
//        return generateJwtToken(roles, account, 1000 * 60 * JWT_REFRESH_TOKEN_EXPIRATION_IN_MINUTES);
//    }

    public String generateJwtToken(
            Map<String, Object> extraClaims,
            Employee account,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtToken(
            Map<String, Object> extraClaims,
            Customer account,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtToken(
            Map<String, Object> extraClaims,
            Admin account,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public boolean isTokenValid(String token, Customer account) {
//        final Long accId = extractId(token);
//        return Objects.equals(accId, account.getCustomerId()) && !isTokenExpired(token);
//    }
//
//    public boolean isTokenValid(String token, Employee account) {
//        final Long accId = extractId(token);
//        return Objects.equals(accId, account.getEmployeeId()) && !isTokenExpired(token);
//    }
//    public boolean isTokenValid(String token, Admin account) {
//        final Long accId = extractId(token);
//        return Objects.equals(accId, account.getId()) && !isTokenExpired(token);
//    }

//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
//    }

//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }


//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getRole(Employee account) {
       return account.getRole().toString();
    }

    private String getRole(Admin account) {
        return account.getRole().toString();
    }
    private String getRole(Customer account) {
        return account.getRole().toString();
    }
}
