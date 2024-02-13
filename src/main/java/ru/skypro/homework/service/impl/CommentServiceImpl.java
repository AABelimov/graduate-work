package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdService adService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Override
    public Comment getComment(Integer id) {
        return commentRepository.findById(id).orElseThrow(); // TODO: todo
    }

    @Override
    public CommentsDto getComments(Integer adId) {
        List<Comment> comments = commentRepository.findAllByAdPk(adId);
        return commentMapper.toCommentsDto(comments);
    }

    @Override
    public CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Ad ad = adService.getAd(adId);
        Comment comment = commentMapper.toEntity(createOrUpdateCommentDto);

        comment.setAd(ad);

        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Comment comment = getComment(commentId);
        comment.setText(createOrUpdateCommentDto.getText());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }
}
