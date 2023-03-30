package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.entity.Comment;
import com.yildirim.springbootrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllPostComments(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto update(Long postId, Long commentId, CommentDto commentDto);

    String deleteComment(Long postId, Long commentId);
}
