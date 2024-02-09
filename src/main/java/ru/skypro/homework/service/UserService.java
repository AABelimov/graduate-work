package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;

public interface UserService {
    void setUserPassword(NewPasswordDto newPasswordDto);

    UserDto getUserInfo();

    UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto);

    void updateUseAvatar(MultipartFile image);
}
