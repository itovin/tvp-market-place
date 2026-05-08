package com.tierraverdemp.backend.auth;

import com.tierraverdemp.backend.config.JwtConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final JwtService jwtservice;

    public JwtTokensResult login(LoginDto loginRequest){
        Authentication authentication = getAuthentication(loginRequest);
        CustomUserDetails customUserDetails = getUserDetails(authentication);
        String accessToken = jwtservice.generateToken(customUserDetails, jwtConfig.getAccessTokenSecretKey(), jwtConfig.getAccessTokenExpiration());
        String refreshToken = jwtservice.generateToken(customUserDetails, jwtConfig.getRefreshTokenSecretKey(), jwtConfig.getRefreshTokenExpiration());

        return new JwtTokensResult(accessToken, refreshToken);
    }
    private CustomUserDetails getUserDetails(Authentication authentication){
        return (CustomUserDetails) authentication.getPrincipal();
    }
    private Authentication getAuthentication(LoginDto loginRequest){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
    }
}
