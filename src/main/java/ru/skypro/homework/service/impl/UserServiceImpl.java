package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void setUserPassword(NewPasswordDto newPasswordDto) {

    }

    @Override
    public UserDto getUserInfo() {
        return new UserDto();
    }

    @Override
    public UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto) {
        return new UpdateUserDto();
    }

    @Override
    public void updateUseAvatar(MultipartFile image) {

    }
}
