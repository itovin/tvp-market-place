package com.tierraverdemp.backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class RegisterUserDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9_]+$", message = "Username must only contain lower case characters, numbers, and/or underscore (_)")
    @Size(min = 4, max = 30, message = "Username must be 6 to 30 characters long")
    private String username;

    @NotBlank
    @Size(min = 8, max = 30, message = "Password must be 8 to 30 characters long")
    private String password;

    private UserRole role;
}
