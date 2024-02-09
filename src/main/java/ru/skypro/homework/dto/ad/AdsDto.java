package ru.skypro.homework.dto.ad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Ads")
public class AdsDto {

    @Schema(description = "total number of ads")
    private Integer count;

    private List<AdDto> results;
}
