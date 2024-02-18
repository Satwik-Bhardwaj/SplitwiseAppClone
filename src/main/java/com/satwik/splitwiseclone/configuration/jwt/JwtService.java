package com.satwik.splitwiseclone.configuration.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    // secret key
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    // expiration time
    @Value("${jwt.expirationTimeInMinutes")
    private long EXPIRATION_TIME;

    // generate token method
    public String generateToken(String userId) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationTime = new Date((EXPIRATION_TIME * 60 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    // get claims
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // get user id method
    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    // get expiration date of token
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    // validate expiration date
    public boolean isTokenExp(String token) {
        Date expDate = getExpirationDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    // validate token itself
    public boolean validateToken(String token, String userId) {
        String tokenUserId = getUserId(token);

        // check if userId matches to token userId and token is not expired
        return (tokenUserId.equals(userId) && !isTokenExp(token));
    }

}
