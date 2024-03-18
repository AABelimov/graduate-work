package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.InvalidValueException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdService adService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public CommentDto createComment(Integer adId,
                                    CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                    Authentication authentication) {
        checkTextComment(createOrUpdateCommentDto.getText());
        User user = userService.getUserByEmail(authentication.getName());
        Ad ad = adService.getAd(adId);
        Comment comment = commentMapper.toEntity(createOrUpdateCommentDto);
        comment.setAd(ad);
        comment.setUser(user);
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public Comment getComment(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Override
    public CommentsDto getComments(Integer adId) {
        List<Comment> comments = commentRepository.findAllByAdPk(adId);
        return commentMapper.toCommentsDto(comments);
    }

    @Override
    public CommentDto updateComment(Integer adId,
                                    Integer commentId,
                                    CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                    Authentication authentication) {
        checkTextComment(createOrUpdateCommentDto.getText());
        if (isAdminOrOwner(commentId, authentication)) {
            Comment comment = getComment(commentId);
            comment.setText(createOrUpdateCommentDto.getText());
            return commentMapper.toCommentDto(commentRepository.save(comment));
        }
        throw new ForbiddenException("No permission to edit this comment");
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        if (isAdminOrOwner(commentId, authentication)) {
            Comment comment = getComment(commentId);
            commentRepository.delete(comment);
            return;
        }
        throw new ForbiddenException("No permission to delete this comment");
    }

    private boolean isAdminOrOwner(Integer commentId, Authentication authentication) {
        Comment comment = getComment(commentId);
        User user = userService.getUserByEmail(authentication.getName());
        return user.equals(comment.getUser()) || user.getRole().equals(Role.ADMIN.name());
    }

    private void checkTextComment(String text) {
        if (text.length() < 8 || text.length() > 64) {
            throw new InvalidValueException("Text length must be from 8 to 64");
        }
    }
}
