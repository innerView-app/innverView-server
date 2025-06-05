package com.dev.innverview.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.dev.innverview.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:0123456789abcdef0123456789abcdef}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private long expiration;

    private Key key;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("uid", user.getId())
                .claim("name", user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        log.debug("Generated JWT for user {}", user.getId());
        return token;
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        Object uid = claims.get("uid");
        if (uid instanceof Integer i) {
            return i.longValue();
        }
        return (Long) uid;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.get("name", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            log.debug("Valid JWT token");
            return true;
        } catch (Exception e) {
            log.debug("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}
