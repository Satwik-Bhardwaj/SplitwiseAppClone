package com.satwik.splitwiseclone.configuration.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    // secret key for access token
    @Value("${jwt.access.secretKey}")
    private String ACCESS_SECRET_KEY;
    // secret key for refresh token
    @Value("${jwt.refresh.secretKey")
    private String REFRESH_SECRET_KEY;
    // expiration time for access token
    @Value("${jwt.access.expirationTimeInMinutes}")
    private long ACCESS_TOKEN_EXP_TIME;
    // expiration time for refresh token
    @Value("${jwt.refresh.expirationTimeInMinutes}")
    private long REFRESH_TOKEN_EXP_TIME;

    // generate access token method
    public String generateAccessToken(String userId) {
        // here we can get the user entity instead of userId to make it more secure
        // also we can create our own claims set to the builder for generate token
        Date issuedAt = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setIssuer("app.splitwise.clone.com")
                .claim("email", "")
                .setExpiration(new Date((ACCESS_TOKEN_EXP_TIME * 60 * 1000) + issuedAt.getTime()))
                .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET_KEY)
                .compact();
    }

    // generate refresh token method
    public String generateRefreshToken(String userId) {
        // here we can get the user entity instead of userId to make it more secure
        // also we can create our own claims set to the builder for generate token
        Date issuedAt = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(new Date((REFRESH_TOKEN_EXP_TIME * 60 * 1000) + issuedAt.getTime()))
                .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET_KEY)
                .compact();
    }

    // get claims
    private Claims getClaims(String token, String secretKey) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public Claims getClaimsOfAccessToken(String accessToken) {
        try {
            return getClaims(accessToken, ACCESS_SECRET_KEY);
        }catch (ExpiredJwtException expiredJwtException) {
            log.error("access token expired!");
            throw new RuntimeException("Access token is expired! Use refresh token to get new refresh token");
        }catch (SignatureException signatureException) {
            log.error("invalid signature of access token");
            throw new RuntimeException("Access token has invalid signature!");
        }
    }

    private Claims getClaimsOfRefreshToken(String refreshToken) {
        try {
            return getClaims(refreshToken, REFRESH_SECRET_KEY);
        }catch (ExpiredJwtException expiredJwtException) {
            log.error("refresh token expired! User have to log in again");
            throw new RuntimeException("Refresh token is expired! Please log in again...");
        }catch (SignatureException signatureException) {
            log.error("invalid signature of refresh token");
            throw new RuntimeException("refresh token has invalid signature!");
        }
    }

    // get user id method
    public String getUserId(String token) {
        return getClaimsOfAccessToken(token).getSubject();
    }

    // get expiration date of token
    private Date getExpirationDate(String token) {
        return getClaimsOfAccessToken(token).getExpiration();
    }

    // validate expiration date
    private boolean isTokenExp(String token) {
        Date expDate = getExpirationDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    // validate token itself
    public boolean validateToken(String token, String userId) {
        String tokenUserId = getUserId(token);

        // check if userId matches to token userId and token is not expired
        return (tokenUserId.equals(userId) && !isTokenExp(token));
    }

    public String generateAccessTokenFromRefresh(String refreshToken) {
        Claims claims = getClaimsOfRefreshToken(refreshToken);
        if(claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
            log.error("refresh token expired! User have to log in again");
            throw new RuntimeException("Refresh token is expired! Please log in again...");
        }

        String userId = claims.getSubject();
        return generateAccessToken(userId);
    }
}
