package ru.skypro.homework.dto.ad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Ad")
public class AdDto {

    @Schema(description = "ad ID")
    private Integer pk;

    @Schema(description = "ad author ID")
    private Integer author;

    @Schema(description = "link to ad image")
    private String image;

    @Schema(description = "ad price")
    private Integer price;

    @Schema(description = "headline")
    private String title;
}
