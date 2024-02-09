package ru.skypro.homework.service;

import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getComments(Integer adId);

    CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto);

    void deleteComment(Integer adId, Integer commentId);

    CommentDto updateComment(Integer adId, Integer commentId);
}
