package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment toEntity(CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Comment comment = new Comment();

        comment.setText(createOrUpdateCommentDto.getText());
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()).getTime());

        return comment;
    }

    public CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setPk(comment.getPk());
        commentDto.setAuthor(comment.getUser().getId());
        commentDto.setAuthorImage(comment.getUser().getAvatar());
        commentDto.setAuthorFirstName(comment.getUser().getFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setText(comment.getText());

        return commentDto;
    }

    public CommentsDto toCommentsDto(List<Comment> comments) {
        CommentsDto commentsDto = new CommentsDto();
        List<CommentDto> commentDtoList = comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());

        commentsDto.setCount(commentDtoList.size());
        commentsDto.setResults(commentDtoList);

        return commentsDto;
    }
}
