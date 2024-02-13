package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.auth.RegisterDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

@Component
public class UserMapper {

    public User toEntity(RegisterDto registerDto) {
        User user = new User();

        user.setEmail(registerDto.getUsername());
        user.setPassword(registerDto.getPassword()); //TODO: скорей всего нужна функция хеширования
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPhone(registerDto.getPhone());
        user.setRole(registerDto.getRole());

        return user;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole().name());
        userDto.setImage(user.getAvatar());

        return userDto;
    }
}
