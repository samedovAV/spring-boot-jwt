package com.samedov.springbootjwt.utils;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.crypto.SecretKey;

public class TokenUtils {

    public static String getUsernameFromToken(String token, SecretKey signingKey) {
        return Jwts
            .parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public static boolean isTokenExpired(String token, SecretKey signingKey) {
        Date expirationDate = Jwts
            .parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
        return expirationDate.before(new Date());
    }

}
