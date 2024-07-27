package com.example.hotsix.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${jwt.salt}")String secret){
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getName(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("name", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Long getId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }



    public Boolean isExpired(String token) {
        log.info("[JWTUtil] 토큰 만료 검증");
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public long getRemainingTime(String token) {
        Date expiration1 = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();

        long currentTimeMillis = System.currentTimeMillis();

        // 남은 시간 계산 (밀리초 단위)
        long remainingTimeMillis = expiration1.getTime() - currentTimeMillis;

        return remainingTimeMillis;
    }

    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }


    //토큰 생성
    public String createJwt(Long Id,String name, String category, String username, String role, Long expiredMs) {
        log.info("[JWTUtil] JWT토큰 생성");
        return Jwts.builder()
                .claim("name",name)
                .claim("id", Id)
                .claim("category",category)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }


    public String createEmailJwt(String email){
        return Jwts.builder()
                .claim("email",email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*10*1000))
                .signWith(secretKey)
                .compact();
    }


    public String createAccessToken(Long id, String name, String username, String role, Long expiredMs) {
        return createJwt(id,name, "access", username, role, expiredMs);
    }

    public String createRefreshToken(Long id,String name, String username, String role, Long expiredMs) {
        return createJwt(id,name,"refresh",username, role, expiredMs);
    }




}
