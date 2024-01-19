package com.samedov.springbootjwt.service;

import com.samedov.springbootjwt.utils.TokenUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() { // todo maybe move to util class too
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts
            .builder()
            .subject(username)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact();
    }

    public String validateToken(String token, String username) {
        boolean isValid = false;

        try {
            String usernameFromToken = TokenUtils.getUsernameFromToken(token, getSigningKey());
            isValid = (usernameFromToken.equals(username) && !TokenUtils.isTokenExpired(token, getSigningKey()));
        } catch (JwtException e) {
            e.printStackTrace(); // todo refactor
        }

        return isValid ? "Token is valid" : "Token is invalid";
    }
}
