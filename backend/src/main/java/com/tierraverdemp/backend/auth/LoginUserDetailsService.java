package com.tierraverdemp.backend.auth;

import com.tierraverdemp.backend.exceptions.LoginFailedException;
import com.tierraverdemp.backend.exceptions.UserNotFoundException;
import com.tierraverdemp.backend.user.User;
import com.tierraverdemp.backend.user.UserRepository;
import com.tierraverdemp.backend.user.UserRole;
import com.tierraverdemp.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LoginUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Long id = user.getId();
            String password = user.getPassword();
            UserRole role = user.getRole();
            return new CustomUserDetails(
                    id,
                    username,
                    password,
                    List.of(new SimpleGrantedAuthority(role.name()))
            );
    }
}
