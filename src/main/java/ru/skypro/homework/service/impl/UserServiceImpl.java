package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.auth.RegisterDto;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.InvalidValueException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Pattern PATTERN_PHONE = Pattern.compile("(^\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}$)");
    private static final Pattern PATTERN_EMAIL = Pattern.compile("(^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$)");

    @Value("${path.to.user.photo}")
    private String photoPath;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public void createUser(RegisterDto registerDto) {
        if (registrationDataIsCorrect(registerDto)) {
            User user = userMapper.toEntity(registerDto);
            String password = encoder.encode(registerDto.getPassword());
            user.setPassword(password);
            userRepository.save(user);
        }
    }

    @Override
    public boolean existsByEmail(String username) {
        return userRepository.existsByEmail(username);
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByEmail(String userName) {
        return userRepository.findByEmail(userName).orElse(null);
    }

    @Override
    public UserDto getUserInfo(Authentication authentication) {
        User user = getUserByEmail(authentication.getName());
        return userMapper.toUserDto(user);
    }

    @Override
    public byte[] getAvatar(Integer id) {
        try {
            User user = getUser(id);
            return Files.readAllBytes(Path.of(user.getAvatar()));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: todo
        }
    }

    @Override
    public void updateUserPassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        checkPassword(newPasswordDto.getNewPassword());
        User user = getUserByEmail(authentication.getName());

        if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ForbiddenException("Incorrect current password");
        }
    }

    @Override
    public UpdateUserDto updateUserInfo(UpdateUserDto updateUserDto, Authentication authentication) {
        User user = null;
        String firstName = updateUserDto.getFirstName();
        String lastName = updateUserDto.getLastName();
        String phone = updateUserDto.getPhone();

        checkFirstName(firstName);
        checkLastName(lastName);
        checkPhone(phone);

        user = getUserByEmail(authentication.getName());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        userRepository.save(user);

        return updateUserDto;
    }

    @Override
    public void updateUserAvatar(MultipartFile avatar, Authentication authentication) {
        try {
            User user = getUserByEmail(authentication.getName());
            uploadAvatar(user, avatar);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: todo
        }
    }

    private void uploadAvatar(User user, MultipartFile avatar) throws IOException {
        Path filepath = Path.of(photoPath, user.hashCode() + "." + StringUtils.getFilenameExtension(avatar.getOriginalFilename()));
        Files.createDirectories(filepath.getParent());
        Files.deleteIfExists(filepath);

        try (
                InputStream is = avatar.getInputStream();
                OutputStream os = Files.newOutputStream(filepath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            user.setAvatar(filepath.toString());
            userRepository.save(user);
        }
    }

    private boolean registrationDataIsCorrect(RegisterDto registerDto) {
        checkPassword(registerDto.getPassword());
        checkFirstName(registerDto.getFirstName());
        checkLastName(registerDto.getLastName());
        checkEmail(registerDto.getUsername());
        checkPhone(registerDto.getPhone());
        return true;
    }

    private void checkPassword(String password) {
        if (password.length() < 8 || password.length() > 16) {
            throw new InvalidValueException("Password length must be from 8 to 16");
        }
    }

    private void checkFirstName(String firstName) {
        if (firstName.length() < 2 || firstName.length() > 16) {
            throw new InvalidValueException("First name length must be from 2 to 16");
        }
    }

    private void checkLastName(String lastName) {
        if (lastName.length() < 2 || lastName.length() > 16) {
            throw new InvalidValueException("Last name length must be from 2 to 16");
        }
    }

    private void checkEmail(String email) {
        Matcher emailMatcher = PATTERN_EMAIL.matcher(email);
        if (!emailMatcher.matches()) {
            throw new InvalidValueException("Invalid email");
        }
    }

    private void checkPhone(String phone) {
        Matcher phoneMatcher = PATTERN_PHONE.matcher(phone);
        if (!phoneMatcher.matches()) {
            throw new InvalidValueException("Phone number must be in the format +7(999)999-99-99");
        }
    }
}
