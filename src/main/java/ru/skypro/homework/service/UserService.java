package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.auth.RegisterDto;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    void updateUserPassword(NewPasswordDto newPasswordDto, Authentication authentication);

    UserDto getUserInfo(Authentication authentication);

    User getUser(Integer id);

    byte[] getAvatar(Integer id);

    UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication);

    void updateUserAvatar(MultipartFile image, Authentication authentication);

    boolean existsByEmail(String username);

    void createUser(RegisterDto registerDto);

    User getUserByEmail(String userName);
}
