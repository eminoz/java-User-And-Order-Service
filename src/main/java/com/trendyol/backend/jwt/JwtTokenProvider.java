package com.trendyol.backend.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${questapp.app.secret}")
    private String APP_SECRET;
    @Value("${questapp.expires.in}")
    private long EXPIRES_IN;

    public String generateJwtToken(Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date date = new Date(new Date().getTime() + EXPIRES_IN);
        String compact = Jwts.builder()
                .setSubject(userDetails.getId())
                .setIssuedAt(new Date()).setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
        System.out.println("compact");
        return compact;
    }

    String getUserIdFromJwt(String token) {
        Claims body = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return body.getSubject();//şifreyi çözüp içinden idyi aldık
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
