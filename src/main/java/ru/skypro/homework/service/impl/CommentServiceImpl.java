package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public CommentsDto getComments(Integer adId) {
        return new CommentsDto();
    }

    @Override
    public CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return new CommentDto();
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {

    }

    @Override
    public CommentDto updateComment(Integer adId, Integer commentId) {
        return new CommentDto();
    }
}
