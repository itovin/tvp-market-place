package com.tierraverdemp.backend.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userDtoToUser(RegisterUserDto registerUserDto){
        return new User(registerUserDto.getName(), registerUserDto.getEmail(), registerUserDto.getUsername(), registerUserDto.getPassword());
    }
}
