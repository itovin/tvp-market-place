package com.tierraverdemp.backend.user;

import com.tierraverdemp.backend.exceptions.EmailAlreadyRegisteredException;
import com.tierraverdemp.backend.exceptions.UsernameAlreadyRegistered;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterUserDto registerRequest){

        String newEmail = registerRequest.getEmail();
        String newUsername = registerRequest.getUsername();
        if(newEmail != null){
            if(isEmailRegistered(newEmail))
                throw new EmailAlreadyRegisteredException("Email address is already registered");
        }
        if(isUsernameRegistered(newUsername))
            throw new UsernameAlreadyRegistered("Username already registered");
        User user = userMapper.userDtoToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        save(user);
    }

    private void save(User user){
        userRepository.save(user);
    }

    private boolean isEmailRegistered(String email){
        return userRepository.existsByEmail(email);
    }

    private boolean isUsernameRegistered(String username){
        return userRepository.existsByUsername(username);
    }
}
