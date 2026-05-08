package com.tierraverdemp.backend.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
