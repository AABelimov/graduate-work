package ru.skypro.homework.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User")
public class UserDto {

    @Schema(description = "user ID")
    private Integer id;

    @Schema(description = "user login")
    private String email;

    @Schema(description = "username")
    private String firstName;

    @Schema(description = "user's last name")
    private String lastName;

    @Schema(description = "user phone")
    private String phone;

    @Schema(description = "user role")
    private String role;

    @Schema(description = "link to user avatar")
    private String image;
}
