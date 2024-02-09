package ru.skypro.homework.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skypro.homework.dto.Role;

@Data
@Schema(name = "Register")
public class RegisterDto {

    @Schema(description = "login", minLength = 4, maxLength = 32)
    private String username;

    @Schema(description = "password", minLength = 8, maxLength = 16)
    private String password;

    @Schema(description = "username", minLength = 2, maxLength = 16)
    private String firstName;

    @Schema(description = "user's last name", minLength = 2, maxLength = 16)
    private String lastName;

    @Schema(description = "user phone", pattern = "\\+7\\s?\\(?\\d{3}\\)?s\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @Schema(description = "user role")
    private Role role;
}
