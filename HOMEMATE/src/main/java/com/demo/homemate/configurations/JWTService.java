package com.demo.homemate.configurations;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.demo.homemate.entities.Admin;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Employee;
import com.demo.homemate.mappings.AccountMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTService {


    private static final String SECRET_KEY = "Homemate-in-your-heart";

//    public static String generateJwt(String username, String password) {
//        long expirationTimeMillis = System.currentTimeMillis() + 3600000 * 6; // 1 hour
//        return Jwts.builder()
//                .claim("username", username)
//                .claim("password", password)
//                .setExpiration(new Date(expirationTimeMillis))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }

    public static String generateJwtAdminToken(Admin account) {
        long  expiration = System.currentTimeMillis() + 3600000 * 6; // 1 hour
        return Jwts
                .builder()
                .setSubject(String.valueOf(account.getRole()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String generateJwtCustomerToken(Customer account) {
        long  expiration = System.currentTimeMillis() + 3600000 * 6; // 1 hour
        return Jwts
                .builder()
                .setSubject(String.valueOf(account.getRole()))
                .claim("User",new AccountMapper().toCustomerResponse(account))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String generateJwtEmployeeToken(Employee account) {
        long  expiration = System.currentTimeMillis() + 3600000 * 6; // 1 hour
        return Jwts
                .builder()
                .setSubject(String.valueOf(account.getRole()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseJwt(String jwt) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static boolean isJwtExpired(Claims claims) {
        try {
            long expirationTimeMillis = claims.getExpiration().getTime();
            long currentTimeMillis = System.currentTimeMillis();
            return expirationTimeMillis < currentTimeMillis;
        } catch (ExpiredJwtException e) {
            return true; // JWT has expired
        } catch (Exception e) {
            return true; // Invalid JWT or other error occurred
        }
    }

    public static void main(String[] args) {
//        String s = generateJwt("abc", "asdf");

    }
}
