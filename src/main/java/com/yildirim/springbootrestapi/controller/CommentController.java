package com.yildirim.springbootrestapi.controller;

import com.yildirim.springbootrestapi.payload.CommentDto;
import com.yildirim.springbootrestapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(name = "id") long postId,
            @RequestBody CommentDto commentDto
    ){
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getAllPostComments(
            @PathVariable(name = "id") long postId
    ){
        return new ResponseEntity<>(commentService.getAllPostComments(postId), HttpStatus.OK);
    }

    @GetMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(name = "id") long postId,
            @PathVariable(name = "commentId") long commentId
    ){
      return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }
    @PutMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(name = "id") long postId,
            @PathVariable(name = "commentId") long commentId,
            @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(commentService.update(postId, commentId, commentDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(name = "id") long postId,
            @PathVariable(name = "commentId") long commentId
    ){
        return new ResponseEntity<>(commentService.deleteComment(postId, commentId), HttpStatus.OK);
    }

}
