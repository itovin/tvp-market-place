package com.tierraverdemp.backend.auth;

import com.tierraverdemp.backend.config.JwtConfig;
import com.tierraverdemp.backend.refreshtoken.RefreshTokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class JwtService {
    private final RefreshTokenService refreshTokenService;

    public String generateToken(CustomUserDetails customerUserDetails, SecretKey secretKey, int tokenExpiration){
        Instant expiresAt = Instant.now().plusSeconds(tokenExpiration);
        String userId = customerUserDetails.getId().toString();
        List<String> roles = customerUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtBuilder jwts = Jwts.builder()
                .subject(userId)
                .claim("role", roles)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey);

        return jwts.compact();

    }

}
