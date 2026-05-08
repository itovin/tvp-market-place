package com.tierraverdemp.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtTokensResult {
    private final String accessToken;
    private final String refreshToken;
}
