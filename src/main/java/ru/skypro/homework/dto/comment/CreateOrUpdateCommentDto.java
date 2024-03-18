package ru.skypro.homework.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CreateOrUpdateComment")
public class CreateOrUpdateCommentDto {

    @Schema(description = "comment text", minLength = 8, maxLength = 64)
    private String text;
}
