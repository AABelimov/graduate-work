package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.auth.RegisterDto;
import ru.skypro.homework.exception.UserAlreadyRegisteredException;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @Override
    public boolean login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean registration(RegisterDto registerDto) {
        String username = registerDto.getUsername();
        if (userService.existsByEmail(username)) {
            throw new UserAlreadyRegisteredException(username);
        }
        userService.createUser(registerDto);
        return true;
    }

}
