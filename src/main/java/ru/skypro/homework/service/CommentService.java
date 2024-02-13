package ru.skypro.homework.service;

import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;

public interface CommentService {

    Comment getComment(Integer id);

    CommentsDto getComments(Integer adId);

    CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto);

    void deleteComment(Integer adId, Integer commentId);

    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto);
}
