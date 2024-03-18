package ru.skypro.homework.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UpdateUser")
public class UpdateUserDto {

    @Schema(description = "user's first name", minLength = 3, maxLength = 10)
    private String firstName;

    @Schema(description = "user's last name", minLength = 3, maxLength = 10)
    private String lastName;

    @Schema(description = "user phone", pattern = "\\+7\\s?\\(?\\d{3}\\)?s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
