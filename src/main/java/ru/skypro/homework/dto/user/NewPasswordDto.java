package ru.skypro.homework.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "NewPassword")
public class NewPasswordDto {

    @Schema(description = "current password", minLength = 8, maxLength = 16)
    private String currentPassword;

    @Schema(description = "new password", minLength = 8, maxLength = 16)
    private String newPassword;
}
