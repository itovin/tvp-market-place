package com.tierraverdemp.backend.refreshtoken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }
}
