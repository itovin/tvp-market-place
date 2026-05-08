package com.tierraverdemp.backend.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
@Getter
@Setter
public class JwtConfig {
    private String accessTokenSecret;
    private String refreshTokenSecret;
    private int accessTokenExpiration;
    private int refreshTokenExpiration;

    public SecretKey getAccessTokenSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
    }
    public SecretKey getRefreshTokenSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
    }
}
