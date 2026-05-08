package com.tierraverdemp.backend.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerUserDtoToUser(RegisterUserDto registerUserDto){
        UserRole role = UserRole.USER;
        if(registerUserDto.getRole() != null)
            role = registerUserDto.getRole();
        return new User(
                registerUserDto.getName(),
                registerUserDto.getEmail(),
                registerUserDto.getUsername(),
                registerUserDto.getPassword(),
                role
        );
    }
}
