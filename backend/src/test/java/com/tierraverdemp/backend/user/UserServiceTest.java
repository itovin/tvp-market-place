package com.tierraverdemp.backend.user;

import com.tierraverdemp.backend.exceptions.EmailAlreadyRegisteredException;
import com.tierraverdemp.backend.exceptions.UserNotFoundException;
import com.tierraverdemp.backend.exceptions.UsernameAlreadyRegistered;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully(){

        RegisterUserDto dto = new RegisterUserDto(
                "Example Sample",
                "sample@email.com",
                "sample",
                "sample123",
                UserRole.USER
        );

        User user = new User();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByUsername(dto.getUsername()))
                .thenReturn(false);

        when(userMapper.registerUserDtoToUser(dto))
                .thenReturn(user);

        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        userService.register(dto);

        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository).save(user);

    }
    @Test
    void shouldThrowExceptionWhenEmailAlreadyRegistered(){

        RegisterUserDto dto = new RegisterUserDto(
                "Example Sample",
                "sample@email.com",
                "sample",
                "sample123",
                UserRole.USER
        );

        User user = new User();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> userService.register(dto));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyRegistered(){

        RegisterUserDto dto = new RegisterUserDto(
                "Example Sample",
                "sample@email.com",
                "sample",
                "sample123",
                UserRole.USER
        );

        User user = new User();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByUsername(dto.getUsername()))
                .thenReturn(true);

        assertThrows(UsernameAlreadyRegistered.class, () -> userService.register(dto));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldReturnUserwhenUsernameExists(){

        User user = new User(
                "Example Sample",
                "sample@email.com",
                "sample",
                "sample123",
                UserRole.USER
        );

        when(userRepository.findByUsername("sample"))
                .thenReturn(Optional.of(user));

        User result = userService.getByUsername("sample");
        assertEquals("sample", result.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist(){
        when(userRepository.findByUsername("sample"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByUsername("sample"));
    }

}
