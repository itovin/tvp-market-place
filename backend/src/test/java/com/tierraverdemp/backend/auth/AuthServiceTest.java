package com.tierraverdemp.backend.auth;

import com.tierraverdemp.backend.config.JwtConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtConfig jwtConfig;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldGenerateAccessAndRefreshToken(){

        LoginDto loginRequest = new LoginDto(
                "sample",
                "sample123"
        );
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        SecretKey accessTokenSecret = mock(SecretKey.class);
        SecretKey refreshTokenSecret = mock(SecretKey.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(customUserDetails);

        when(jwtConfig.getAccessTokenSecretKey())
                .thenReturn(accessTokenSecret);

        when(jwtConfig.getAccessTokenExpiration())
                .thenReturn(900);

        when(jwtService.generateToken(customUserDetails, accessTokenSecret, 900))
                .thenReturn("accessToken");

        when(jwtConfig.getRefreshTokenSecretKey())
                .thenReturn(refreshTokenSecret);

        when(jwtConfig.getRefreshTokenExpiration())
                .thenReturn(604800);

        when(jwtService.generateToken(customUserDetails, refreshTokenSecret, 604800))
                .thenReturn("refreshToken");

        JwtTokensResult result = authService.login(loginRequest);

        assertEquals("accessToken", result.getAccessToken());
        assertEquals("refreshToken", result.getRefreshToken());
    }

    @Test
    void shouldThrowBadCredentialsException(){
        LoginDto loginRequest = new LoginDto(
                "sample",
                "sample123"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Login failed."));

        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));

        verify(jwtService, never())
                .generateToken(any(), any(), anyInt());
    }
    @Test
    void shouldThrowUsernameNotFoundException(){
        LoginDto loginRequest = new LoginDto(
                "sample",
                "sample123"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new UsernameNotFoundException("Login failed."));

        assertThrows(UsernameNotFoundException
                .class, () -> authService.login(loginRequest));

        verify(jwtService, never())
                .generateToken(any(), any(), anyInt());
    }
}
