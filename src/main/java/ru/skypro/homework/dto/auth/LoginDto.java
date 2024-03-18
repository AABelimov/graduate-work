package ru.skypro.homework.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Login")
public class LoginDto {

    @Schema(description = "login", minLength = 4, maxLength = 32)
    private String username;

    @Schema(description = "password", minLength = 8, maxLength = 16)
    private String password;
}
