package ru.skypro.homework.dto.ad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "CreateOrUpdateAd")
public class CreateOrUpdateAdDto {

    @Schema(description = "headline", minLength = 4, maxLength = 32)
    private String title;

    @Schema(description = "ad price", minimum = "0", maximum = "10000000")
    private Integer price;

    @Schema(description = "ad description", minLength = 8, maxLength = 64)
    private String description;
}
