package com.tierraverdemp.backend.auth;

import com.tierraverdemp.backend.config.JwtConfig;
import com.tierraverdemp.backend.user.RegisterUserDto;
import com.tierraverdemp.backend.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@AllArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerRequest){
        userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginRequest, HttpServletResponse response){
        JwtTokensResult jwtTokensResult = authService.login(loginRequest);
        Cookie cookie = buildCookie(jwtTokensResult.getRefreshToken(), jwtConfig.getRefreshTokenExpiration());
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(jwtTokensResult.getAccessToken()));

    }

    private Cookie buildCookie(String refreshToken, int expiration){
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(expiration);
        cookie.setSecure(true);
        return cookie;
    }
}
