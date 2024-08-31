package com.lth.bookingcare.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class JwtService {
    private SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());
    public Claims buildClaims(int userId, String email, Collection<String> c) {
        Claims claims = Jwts.claims();
        claims.put("typeToken", "Bearer");
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("roles", c);
        return claims;
    }
    public boolean isValidToken(String token) {
        token = Strings.replace(token, "Bearer ", "");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    public int getIdFromToken(String token) {
        if(!StringUtils.hasLength(token)) {
            throw new IllegalArgumentException("Jwt is empty or null !");
        }
        token = Strings.replace(token, "Bearer ", "");
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("userId", Integer.class);
    }
    public String generateToken(CustomUserDetail customUserDetail) {
        Date now = new Date();
        Date expirationDate = new Date(now.getDate() + 8640000);
        Set<String> roles = customUserDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Claims claims = buildClaims((int)customUserDetail.getUserId(), customUserDetail.getUsername(), roles);
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .setClaims(claims)
                .signWith(key)
                .compact();
    }
}
