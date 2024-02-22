package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;

public interface CommentService {

    Comment getComment(Integer id);

    CommentsDto getComments(Integer adId);

    CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

    void deleteComment(Integer adId, Integer commentId, Authentication authentication);

    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);
}
