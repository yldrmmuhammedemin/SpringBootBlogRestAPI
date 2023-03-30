package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.entity.Comment;
import com.yildirim.springbootrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllPostComments(long postId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto update(long postId, long commentId, CommentDto commentDto);

    String deleteComment(long postId, long commentId);
}
