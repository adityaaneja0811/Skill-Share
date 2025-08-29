package com.org.skillshare.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final Key key;
    private final long expiry;

    private final SecretKey key1 = Keys.secretKeyFor(SignatureAlgorithm.HS512);




//    public JwtUtil(String base64Secret, long expiryMillis) {
//        this.key = Keys.hmacShaKeyFor(
//                io.jsonwebtoken.io.Decoders.BASE64.decode(base64Secret)
//        );
//        this.expiry = expiryMillis;
//    }

    public JwtUtil(String secret, long expiryMillis) {
        // HS512 requires at least 512 bits (64 bytes) secret
        // secret = (String) key1;
        //this.key = Keys.hmacShaKeyFor(secret.getBytes());

        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.expiry = expiryMillis;
    }


    public String generate(String subject, Map<String, Object> claims){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiry))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean validate(String token, String email) {
        try {
            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return subject.equals(email);
        } catch (Exception e) {
            return false;
        }
    }
    public String getSubject(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValid(String token){
        try { Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); return true; }
        catch (JwtException e){ return false; }
    }
}
