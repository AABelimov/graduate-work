package ru.skypro.homework.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Comments")
public class CommentsDto {

    @Schema(description = "total number of comments")
    private Integer count;

    private List<CommentDto> results;
}
