package ru.skypro.homework.dto.ad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ExtendedAd")
public class ExtendedAdDto {

    @Schema(description = "ad Id")
    private Integer pk;

    @Schema(description = "ad author's name")
    private String authorFirstName;

    @Schema(description = "last name of the ad author")
    private String authorLastName;

    @Schema(description = "ad description")
    private String description;

    @Schema(description = "ad author login")
    private String email;

    @Schema(description = "link to ad image")
    private String image;

    @Schema(description = "ad author's phone number")
    private String phone;

    @Schema(description = "ad price")
    private Integer price;

    @Schema(description = "headline")
    private String title;
}
