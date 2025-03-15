package com.example.antmall.common.util;

import com.example.antmall.business.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret:defaultSecretKey}")
    private String secret;

    @Value("${jwt.expiration:3600}")
    private Long expiration;

    // 生成 Token
    public String generateToken(String username, UserRole userRole) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 解析 Token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
