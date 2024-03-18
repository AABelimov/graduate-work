package ru.skypro.homework.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Comment")
public class CommentDto {

    @Schema(description = "comment ID")
    private Integer pk;

    @Schema(description = "comment author ID")
    private Integer author;

    @Schema(description = "link to the avatar of the comment author")
    private String authorImage;

    @Schema(description = "comment author's name")
    private String authorFirstName;

    @Schema(description = "date and time the comment was created in milliseconds since 00:00:00 01.01.1970")
    private Long createdAt;

    @Schema(description = "comment text")
    private String text;
}
