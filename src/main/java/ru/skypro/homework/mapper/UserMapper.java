package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.auth.RegisterDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

@Component
public class UserMapper {

    @Value("${path.to.default.user.photo}")
    private String photoPath;

    public User toEntity(RegisterDto registerDto) {
        User user = new User();

        user.setEmail(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPhone(registerDto.getPhone());
        user.setRole(registerDto.getRole().name());
        user.setAvatar(photoPath);

        return user;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        userDto.setImage("/users/" + user.getId() + "/avatar");

        return userDto;
    }
}
