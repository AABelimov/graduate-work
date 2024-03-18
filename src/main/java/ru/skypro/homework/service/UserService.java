package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.auth.RegisterDto;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    void createUser(RegisterDto registerDto);

    boolean existsByEmail(String username);

    User getUser(Integer id);

    User getUserByEmail(String userName);

    UserDto getUserInfo(Authentication authentication);

    byte[] getAvatar(Integer id);

    void updateUserPassword(NewPasswordDto newPasswordDto, Authentication authentication);

    UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication);

    void updateUserAvatar(MultipartFile image, Authentication authentication);
}
